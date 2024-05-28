package application.banco.controller;

import application.banco.model.Contrato;
import application.banco.service.serviceImpl.ContratoService;
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

public class ContratoController {


    @FXML
    private Button actualizarBtn;

    @FXML
    private Button crearBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private TableView<Contrato> tableView;

    @FXML
    private TableColumn<Contrato, String> cargoTc;

    @FXML
    private TableColumn<Contrato, String> codigoTc;

    @FXML
    private TableColumn<Contrato, String> empleadoTc;

    @FXML
    private TableColumn<Contrato, String> sucursalTc;

    @FXML
    private TableColumn<Contrato, LocalDate> fechaFinTc;

    @FXML
    private TableColumn<Contrato, LocalDate> fechaInicioTc;

    @FXML
    private BorderPane mainPane;

    private Form form;

    private StringProperty codigoField;
    private StringProperty empleadoField;
    private StringProperty cargoField;
    private StringProperty sucursalField;
    private SimpleObjectProperty<LocalDate> fechaInicioField;
    private SimpleObjectProperty<LocalDate> fechaFinField;

    private StringProperty claveField;

    private EstadoAplicacion estadoAplicacion;

    private ContratoService service;

    ObservableList<Contrato> contratosObservableList = FXCollections.observableArrayList();


    public void limpiarDatos() {
        codigoField.set("");
        empleadoField.set("");
        cargoField.set("");
        sucursalField.set("");
        fechaInicioField.set(null);
        fechaFinField.set(null);
    }

    @FXML
    void initialize() {
        codigoField = new SimpleStringProperty("");
        empleadoField = new SimpleStringProperty("");
        cargoField = new SimpleStringProperty("");
        sucursalField = new SimpleStringProperty("");
        fechaInicioField = new SimpleObjectProperty<>();
        fechaFinField = new SimpleObjectProperty<>();

        estadoAplicacion = EstadoAplicacion.getInstance();

        service = new ContratoService();

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
                        Field.ofStringType(empleadoField)
                                .required(true)
                                .label("Empleado"),
                        Field.ofStringType(cargoField)
                                .required(true)
                                .label("Cargo"),
                        Field.ofStringType(sucursalField)
                                .required(true)
                                .label("Sucursal"),
                        Field.ofDate(fechaInicioField)
                                .required(true)
                                .label("Fecha Inicio"),
                        Field.ofDate(fechaFinField)
                                .required(true)
                                .label("Fecha Fin")
                )
        );

        mainPane.setCenter(new FormRenderer(form));


        codigoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCodigo())));
        empleadoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getEmpleado())));
        cargoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCargo())));
        sucursalTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getSucursal())));
        fechaInicioTc.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getFechaInicio().toLocalDate()));
        fechaFinTc.setCellValueFactory(cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getFechaFin().toLocalDate()));

        tableView.getItems().clear();
        tableView.setItems(contratosObservableList);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarInformacionContrato(newSelection);
            }
        });
        llenarTabla();
    }

    private void llenarTabla() {
        Thread thread = new Thread(() -> {
            List<Contrato> result = service.buscarTodos();
            System.out.println(result);
            contratosObservableList.setAll(result);
        });
        Platform.runLater(thread);
    }


    private void mostrarInformacionContrato(Contrato contrato) {
        codigoField.set(String.valueOf(contrato.getCodigo()));
        empleadoField.set(String.valueOf(contrato.getEmpleado()));
        cargoField.set(String.valueOf(contrato.getCargo()));
        sucursalField.set(String.valueOf(contrato.getSucursal()));
        fechaInicioField.set(contrato.getFechaInicio().toLocalDate());
        fechaFinField.set(contrato.getFechaFin().toLocalDate());
    }

    @FXML
    void actualizarContrato(ActionEvent event) {
        form.persist();
        Contrato toSave = Contrato.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .cargo(Integer.parseInt(cargoField.get()))
                .empleado(Integer.parseInt(empleadoField.get()))
                .sucursal(Integer.parseInt(sucursalField.get()))
                .fechaInicio(Date.valueOf(fechaInicioField.get()))
                .fechaFin(Date.valueOf(fechaFinField.get()))
                .build();
        service.actualizarContrato(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Contrato actualizado correctamente").show();
    }

    @FXML
    void crearContrato(ActionEvent event) {
        form.persist();
        Contrato toSave = Contrato.builder()
                .cargo(Integer.parseInt(cargoField.get()))
                .empleado(Integer.parseInt(empleadoField.get()))
                .sucursal(Integer.parseInt(sucursalField.get()))
                .fechaInicio(Date.valueOf(fechaInicioField.get()))
                .fechaFin(Date.valueOf(fechaFinField.get()))
                .build();
        service.crearContrato(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Contrato creado correctamente").show();
    }

    @FXML
    void eliminarContrato(ActionEvent event) {
        form.persist();
        Contrato toSave = Contrato.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .build();
        service.eliminarContrato(toSave.getCodigo());
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Contrato eliminado correctamente").show();
    }
}
