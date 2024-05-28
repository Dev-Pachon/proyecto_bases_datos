package application.banco.controller;

import application.banco.error.CustomError;
import application.banco.model.Cargo;
import application.banco.model.Departamento;
import application.banco.model.Sucursal;
import application.banco.service.serviceImpl.CargoService;
import application.banco.service.serviceImpl.DepartamentoService;
import application.banco.service.serviceImpl.NivelService;
import application.banco.service.serviceImpl.SucursalService;
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

public class CargoController {

    @FXML
    private Button actualizarBtn;

    @FXML
    private Button crearBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private TableView<Cargo> tableView;

    @FXML
    private TableColumn<Cargo, String> nombreTc;

    @FXML
    private TableColumn<Cargo, String> codigoTc;

    @FXML
    private TableColumn<Cargo, String> salarioTc;


    @FXML
    private BorderPane mainPane;

    private Form form;

    private StringProperty codigoField;
    private StringProperty nombreField;
    private StringProperty salarioField;

    private EstadoAplicacion estadoAplicacion;

    private CargoService service;

    ObservableList<Cargo> observableList = FXCollections.observableArrayList();


    public void limpiarDatos() {
        codigoField.set("");
        nombreField.set("");
        salarioField.set("");
    }

    @FXML
    void initialize() {
        estadoAplicacion = EstadoAplicacion.getInstance();

        service = new CargoService();

        NivelService nivel = new NivelService();

        codigoField = new SimpleStringProperty("");
        nombreField = new SimpleStringProperty("");
        salarioField = new SimpleStringProperty("");


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
                        Field.ofStringType(salarioField)
                                .id("salario")
                                .required(true)
                                .label("Salario")
                )
        );

        mainPane.setCenter(new FormRenderer(form));


        codigoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCodigo())));
        nombreTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        salarioTc.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getSalario())));

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
            List<Cargo> result = service.buscarTodos();
            System.out.println(result);
            observableList.setAll(result);
        });
        Platform.runLater(thread);
    }


    private void mostrarInformacionContrato(Cargo cargo) {
        codigoField.set(String.valueOf(cargo.getCodigo()));
        nombreField.set(cargo.getNombre());
        salarioField.set(String.valueOf(cargo.getSalario()));
    }

    @FXML
    void actualizar(ActionEvent event) {
        form.persist();
        Cargo toSave = Cargo.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .nombre(nombreField.get())
                .salario(Double.valueOf(salarioField.get()))
                .build();
        service.actualizarCargo(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Cargo actualizado correctamente").show();
    }

    @FXML
    void crear(ActionEvent event) {
        form.persist();
        Cargo toSave = Cargo.builder()
                .nombre(nombreField.get())
                .salario(Double.valueOf(salarioField.get()))
                .build();

        service.crearCargo(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Cargo creado correctamente").show();
    }

    @FXML
    void eliminar(ActionEvent event) {
        form.persist();
        Sucursal toSave = Sucursal.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .build();
        try {
            service.eliminarCargo(toSave.getCodigo());
            llenarTabla();
            form.reset();
            limpiarDatos();
            new Alert(Alert.AlertType.INFORMATION, "Sucursal eliminado correctamente").show();

        } catch (CustomError e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        }
    }
}
