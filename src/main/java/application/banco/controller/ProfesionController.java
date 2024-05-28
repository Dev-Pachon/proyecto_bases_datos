package application.banco.controller;

import application.banco.error.CustomError;
import application.banco.model.Cargo;
import application.banco.model.Departamento;
import application.banco.model.Profesion;
import application.banco.model.Sucursal;
import application.banco.service.serviceImpl.CargoService;
import application.banco.service.serviceImpl.DepartamentoService;
import application.banco.service.serviceImpl.NivelService;
import application.banco.service.serviceImpl.ProfesionService;
import application.banco.state.EstadoAplicacion;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class ProfesionController {
    @FXML
    private Button actualizarBtn;

    @FXML
    private Button crearBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private TableView<Profesion> tableView;

    @FXML
    private TableColumn<Profesion, String> nombreTc;

    @FXML
    private TableColumn<Profesion, String> codigoTc;

    @FXML
    private TableColumn<Profesion, String> descripcionTc;


    @FXML
    private BorderPane mainPane;

    private Form form;

    private StringProperty codigoField;
    private StringProperty nombreField;
    private StringProperty descripcionField;

    private EstadoAplicacion estadoAplicacion;

    private ProfesionService service;

    ObservableList<Profesion> observableList = FXCollections.observableArrayList();


    public void limpiarDatos() {
        codigoField.set("");
        nombreField.set("");
        descripcionField.set("");
    }

    @FXML
    void initialize() {
        estadoAplicacion = EstadoAplicacion.getInstance();

        service = new ProfesionService();

        NivelService nivel = new NivelService();

        codigoField = new SimpleStringProperty("");
        nombreField = new SimpleStringProperty("");
        descripcionField = new SimpleStringProperty("");


        if (!(nivel.buscarPorId(estadoAplicacion.getUsuario().getNivel()).getNombre().equals("Principal"))) {
            actualizarBtn.setDisable(true);
            crearBtn.setDisable(true);
            eliminarBtn.setDisable(true);
        }

        limpiarDatos();

        form = Form.of(
                Group.of(Field.ofStringType(codigoField)
                                .id("cod")
                                .label("Codigo")
                                .required(true)
                                .editable(false),
                        Field.ofStringType(nombreField)
                                .id("nombre")
                                .required(true)
                                .label("Nombre"),
                        Field.ofStringType(descripcionField)
                                .id("descripcion")
                                .required(true)
                                .label("Descripcion")
                )
        );

        mainPane.setCenter(new FormRenderer(form));


        codigoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCodigo())));
        descripcionTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        nombreTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        tableView.getItems().clear();
        tableView.setItems(observableList);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarInformacionContrato(newSelection);
            }
        });
        llenarTabla();
    }

    private void llenarTabla() {
        Thread thread = new Thread(() -> {
            List<Profesion> result = service.buscarTodos();
            System.out.println(result);
            observableList.setAll(result);
        });
        Platform.runLater(thread);
    }


    private void mostrarInformacionContrato(Profesion profesion) {
        codigoField.set(String.valueOf(profesion.getCodigo()));
        nombreField.set(profesion.getNombre());
        descripcionField.set(profesion.getDescripcion());
    }

    @FXML
    void actualizar(ActionEvent event) {
        form.persist();
        Profesion toSave = Profesion.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .nombre(nombreField.get())
                .descripcion(descripcionField.get())
                .build();
        service.actualizarProfesion(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Profesion actualizado correctamente").show();
    }

    @FXML
    void crear(ActionEvent event) {
        form.persist();
        Profesion toSave = Profesion.builder()
                .nombre(nombreField.get())
                .descripcion(descripcionField.get())
                .build();

        service.crearProfesion(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Profesion creado correctamente").show();
    }

    @FXML
    void eliminar(ActionEvent event) {
        form.persist();
        Sucursal toSave = Sucursal.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .build();
        try {
            service.eliminarProfesion(toSave.getCodigo());
            llenarTabla();
            form.reset();
            limpiarDatos();
            new Alert(Alert.AlertType.INFORMATION, "Profesion eliminado correctamente").show();

        } catch (CustomError e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        }
    }
}
