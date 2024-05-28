package application.banco.controller;

import application.banco.error.CustomError;
import application.banco.model.Contrato;
import application.banco.model.Departamento;
import application.banco.model.Empleado;
import application.banco.service.serviceImpl.ContratoService;
import application.banco.service.serviceImpl.DepartamentoService;
import application.banco.service.serviceImpl.EmpleadoService;
import application.banco.service.serviceImpl.NivelService;
import application.banco.state.EstadoAplicacion;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmpleadoController {
    @FXML
    private Button actualizarBtn;

    @FXML
    private Button crearBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private TableView<Empleado> tableView;

    @FXML
    private TableColumn<Empleado, String> nombreTc;

    @FXML
    private TableColumn<Empleado, String> codigoTc;

    @FXML
    private TableColumn<Empleado, String> cedulaTc;

    @FXML
    private TableColumn<Empleado, String> direccionTc;

    @FXML
    private TableColumn<Empleado, String> telefonoTc;

    @FXML
    private TableColumn<Empleado, String> usuarioTc;

    @FXML
    private BorderPane mainPane;

    private Form form;

    private StringProperty codigoField;
    private StringProperty cedulaField;
    private StringProperty nombreField;
    private StringProperty direccionField;
    private StringProperty telefonoField;
    private StringProperty usuarioField;


    private EstadoAplicacion estadoAplicacion;

    private EmpleadoService service;

    ObservableList<Empleado> observableList = FXCollections.observableArrayList();


    public void limpiarDatos() {
        codigoField.set("");
        cedulaField.set("");
        nombreField.set("");
        direccionField.set("");
        telefonoField.set(null);
        usuarioField.set(null);
    }

    @FXML
    void initialize() {
        codigoField = new SimpleStringProperty("");
        cedulaField = new SimpleStringProperty("");
        nombreField = new SimpleStringProperty("");
        direccionField = new SimpleStringProperty("");
        telefonoField = new SimpleStringProperty("");
        usuarioField = new SimpleStringProperty("");

        estadoAplicacion = EstadoAplicacion.getInstance();

        service = new EmpleadoService();

        NivelService nivel = new NivelService();

        if (!(nivel.buscarPorId(estadoAplicacion.getUsuario().getNivel()).getNombre().equals("Principal"))) {
            actualizarBtn.setDisable(true);
            crearBtn.setDisable(true);
            eliminarBtn.setDisable(true);
        }

        limpiarDatos();

        form = Form.of(
                Group.of(Field.ofStringType(codigoField)
                                .label("Codigo")
                                .editable(false),
                        Field.ofStringType(cedulaField)
                                .required(true)
                                .label("Cedula"),
                        Field.ofStringType(nombreField)
                                .required(true)
                                .label("Nombre"),
                        Field.ofStringType(direccionField)
                                .required(true)
                                .label("Direccion"),
                        Field.ofStringType(telefonoField)
                                .required(true)
                                .label("Telefono"),
                        Field.ofStringType(usuarioField)
                                .required(true)
                                .label("Usuario")
                )
        );

        mainPane.setCenter(new FormRenderer(form));


        codigoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCodigo())));
        cedulaTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        nombreTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        direccionTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        telefonoTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        usuarioTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getUsuario())));

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
            List<Empleado> result = service.buscarTodos();
            System.out.println(result);
            observableList.setAll(result);
        });
        Platform.runLater(thread);
    }


    private void mostrarInformacionContrato(Empleado empleado) {
        codigoField.set(String.valueOf(empleado.getCodigo()));
        cedulaField.set(empleado.getCedula());
        nombreField.set(empleado.getNombre());
        direccionField.set(empleado.getDireccion());
        telefonoField.set(empleado.getTelefono());
        usuarioField.set(String.valueOf(empleado.getUsuario()));
    }

    @FXML
    void actualizar(ActionEvent event) {
        form.persist();
        Empleado toSave = Empleado.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .nombre(nombreField.get())
                .cedula(cedulaField.get())
                .direccion(direccionField.get())
                .telefono(telefonoField.get())
                .usuario(Integer.parseInt(usuarioField.get()))
                .build();
        service.actualizarEmpleado(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Contrato actualizado correctamente").show();
    }

    @FXML
    void crear(ActionEvent event) {
        form.persist();
        Empleado toSave = Empleado.builder()
                .nombre(nombreField.get())
                .cedula(cedulaField.get())
                .direccion(direccionField.get())
                .telefono(telefonoField.get())
                .usuario(Integer.parseInt(usuarioField.get()))
                .build();
        service.crearEmpleado(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Contrato creado correctamente").show();
    }

    @FXML
    void eliminar(ActionEvent event) {
        form.persist();
        Empleado toSave = Empleado.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .build();
        service.eliminarEmpleado(toSave.getCodigo());
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Contrato eliminado correctamente").show();
    }
}
