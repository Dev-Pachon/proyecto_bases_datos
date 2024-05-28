package application.banco.controller;

import application.banco.error.CustomError;
import application.banco.model.Prioridad;
import application.banco.service.serviceImpl.NivelService;
import application.banco.service.serviceImpl.PrioridadService;
import application.banco.state.EstadoAplicacion;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.application.Platform;
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

public class PrioridadController {

    @FXML
    private Button actualizarBtn;

    @FXML
    private Button crearBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private TableView<Prioridad> tableView;

    @FXML
    private TableColumn<Prioridad, String> nombreTc;

    @FXML
    private TableColumn<Prioridad, String> codigoTc;

    @FXML
    private BorderPane mainPane;

    private Form form;

    private StringProperty codigoField;
    private StringProperty nombreField;

    private EstadoAplicacion estadoAplicacion;

    private PrioridadService service;

    ObservableList<Prioridad> departamentosObservableList = FXCollections.observableArrayList();


    public void limpiarDatos() {
        codigoField.set("");
        nombreField.set("");
    }

    @FXML
    void initialize() {
        estadoAplicacion = EstadoAplicacion.getInstance();

        service = new PrioridadService();

        NivelService nivel = new NivelService();

        codigoField = new SimpleStringProperty("");
        nombreField = new SimpleStringProperty("");

        if (!(nivel.buscarPorId(estadoAplicacion.getUsuario().getNivel()).getNombre().equals("Principal"))) {
            actualizarBtn.setDisable(true);
            crearBtn.setDisable(true);
            eliminarBtn.setDisable(true);
        }

        limpiarDatos();

        form = Form.of(
                Group.of(Field.ofStringType(codigoField)
                                .label("Codigo")
                                .required(true)
                                .editable(false),
                        Field.ofStringType(nombreField)
                                .required(true)
                                .label("Nombre")
                )
        );

        mainPane.setCenter(new FormRenderer(form));


        codigoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCodigo())));
        nombreTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        tableView.getItems().clear();
        tableView.setItems(departamentosObservableList);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarInformacionContrato(newSelection);
            }
        });
        llenarTabla();
    }

    private void llenarTabla() {
        Thread thread = new Thread(() -> {
            List<Prioridad> result = service.buscarTodos();
            System.out.println(result);
            departamentosObservableList.setAll(result);
        });
        Platform.runLater(thread);
    }


    private void mostrarInformacionContrato(Prioridad prioridad) {
        codigoField.set(String.valueOf(prioridad.getCodigo()));
        nombreField.set(String.valueOf(prioridad.getNombre()));

    }

    @FXML
    void actualizar(ActionEvent event) {
        form.persist();
        Prioridad toSave = Prioridad.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .nombre(nombreField.get())
                .build();
        service.actualizarPrioridad(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Prioridad actualizado correctamente").show();
    }

    @FXML
    void crear(ActionEvent event) {
        form.persist();
        Prioridad toSave = Prioridad.builder()
                .nombre(nombreField.get())
                .build();
        service.crearPrioridad(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Prioridad creado correctamente").show();
    }

    @FXML
    void eliminar(ActionEvent event) {
        form.persist();
        Prioridad toSave = Prioridad.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .build();
        try {
            service.eliminarPrioridad(toSave.getCodigo());
            llenarTabla();
            form.reset();
            limpiarDatos();
            new Alert(Alert.AlertType.INFORMATION, "Prioridad eliminado correctamente").show();

        } catch (CustomError e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        }
    }
}
