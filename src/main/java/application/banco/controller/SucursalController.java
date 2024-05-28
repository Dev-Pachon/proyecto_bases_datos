package application.banco.controller;

import application.banco.error.CustomError;
import application.banco.model.Departamento;
import application.banco.model.Sucursal;
import application.banco.service.serviceImpl.DepartamentoService;
import application.banco.service.serviceImpl.NivelService;
import application.banco.service.serviceImpl.SucursalService;
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

import java.util.List;

public class SucursalController {
    @FXML
    private Button actualizarBtn;

    @FXML
    private Button crearBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private TableView<Sucursal> tableView;

    @FXML
    private TableColumn<Sucursal, String> nombreTc;

    @FXML
    private TableColumn<Sucursal, String> codigoTc;

    @FXML
    private TableColumn<Sucursal, String> presupuestoTc;

    @FXML
    private TableColumn<Sucursal, String> municipioTc;

    @FXML
    private TableColumn<Sucursal, String> directorTc;


    @FXML
    private BorderPane mainPane;

    private Form form;

    private StringProperty codigoField;
    private StringProperty nombreField;
    private StringProperty presupuestoField;
    private StringProperty municipioField;
    private StringProperty directorField;

    private EstadoAplicacion estadoAplicacion;

    private SucursalService service;

    ObservableList<Sucursal> observableList = FXCollections.observableArrayList();


    public void limpiarDatos() {
        codigoField.set("");
        nombreField.set("");
        presupuestoField.set("");
        municipioField.set("");
        directorField.set("");
    }

    @FXML
    void initialize() {
        estadoAplicacion = EstadoAplicacion.getInstance();

        service = new SucursalService();

        NivelService nivel = new NivelService();

        codigoField = new SimpleStringProperty("");
        nombreField = new SimpleStringProperty("");
        presupuestoField = new SimpleStringProperty("");
        municipioField = new SimpleStringProperty("");
        directorField = new SimpleStringProperty("");

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
                        Field.ofStringType(presupuestoField)
                                .id("presupuesto")
                                .required(true)
                                .label("Presupuesto"),
                        Field.ofStringType(municipioField)
                                .id("municipio")
                                .required(true)
                                .label("Municipio"),
                        Field.ofStringType(directorField)
                                .id("director")
                                .required(true)
                                .label("Director")
                )
        );

        mainPane.setCenter(new FormRenderer(form));


        codigoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCodigo())));
        nombreTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        presupuestoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getPresupuesto())));
        municipioTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMunicipio())));
        directorTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getDirector())));

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
            List<Sucursal> result = service.buscarTodos();
            System.out.println(result);
            observableList.setAll(result);
        });
        Platform.runLater(thread);
    }


    private void mostrarInformacionContrato(Sucursal sucursal) {
        codigoField.set(String.valueOf(sucursal.getCodigo()));
        nombreField.set(sucursal.getNombre());
        presupuestoField.set(String.valueOf(sucursal.getPresupuesto()));
        municipioField.set(String.valueOf(sucursal.getMunicipio()));
        directorField.set(String.valueOf(sucursal.getDirector()));
    }

    @FXML
    void actualizar(ActionEvent event) {
        form.persist();
        Sucursal toSave = Sucursal.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .nombre(nombreField.get())
                .presupuesto(Double.valueOf(presupuestoField.get()))
                .municipio(Integer.parseInt(municipioField.get()))
                .director(Integer.parseInt(directorField.get()))
                .build();
        service.actualizarSucursal(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Sucursal actualizado correctamente").show();
    }

    @FXML
    void crear(ActionEvent event) {
        form.persist();
        Sucursal toSave = Sucursal.builder()
                .nombre(nombreField.get())
                .presupuesto(Double.valueOf(presupuestoField.get()))
                .municipio(Integer.parseInt(municipioField.get()))
                .director(Integer.parseInt(directorField.get()))
                .build();

        service.crearSucursal(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Sucursal creado correctamente").show();
    }

    @FXML
    void eliminar(ActionEvent event) {
        form.persist();
        Sucursal toSave = Sucursal.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .build();
        try {
            service.eliminarSucursal(toSave.getCodigo());
            llenarTabla();
            form.reset();
            limpiarDatos();
            new Alert(Alert.AlertType.INFORMATION, "Sucursal eliminado correctamente").show();

        } catch (CustomError e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        }
    }
}
