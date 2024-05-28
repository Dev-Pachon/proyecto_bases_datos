package application.banco.controller;

import application.banco.error.CustomError;
import application.banco.model.Municipio;
import application.banco.model.Prioridad;
import application.banco.service.serviceImpl.MunicipioService;
import application.banco.service.serviceImpl.NivelService;
import application.banco.service.serviceImpl.PrioridadService;
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

public class MunicipioController {
    @FXML
    private Button actualizarBtn;

    @FXML
    private Button crearBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private TableView<Municipio> tableView;

    @FXML
    private TableColumn<Municipio, String> nombreTc;

    @FXML
    private TableColumn<Municipio, String> codigoTc;

    @FXML
    private TableColumn<Municipio, String> poblacionTc;

    @FXML
    private TableColumn<Municipio, String> prioridadTc;

    @FXML
    private TableColumn<Municipio, String> departamentoTc;

    @FXML
    private BorderPane mainPane;

    private Form form;

    private StringProperty codigoField;
    private StringProperty nombreField;
    private StringProperty poblacionField;
    private StringProperty prioridadField;
    private StringProperty departamentoField;

    private EstadoAplicacion estadoAplicacion;

    private MunicipioService service;

    ObservableList<Municipio> observableList = FXCollections.observableArrayList();


    public void limpiarDatos() {
        codigoField.set("");
        nombreField.set("");
        poblacionField.set("");
        prioridadField.set("");
        departamentoField.set("");
    }

    @FXML
    void initialize() {
        estadoAplicacion = EstadoAplicacion.getInstance();

        service = new MunicipioService();

        NivelService nivel = new NivelService();

        codigoField = new SimpleStringProperty("");
        nombreField = new SimpleStringProperty("");
        poblacionField = new SimpleStringProperty("");
        prioridadField = new SimpleStringProperty("");
        departamentoField = new SimpleStringProperty("");

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
                                .label("Nombre"),
                        Field.ofStringType(poblacionField)
                                .required(true)
                                .label("Poblacion"),
                        Field.ofStringType(prioridadField)
                                .required(true)
                                .label("Prioridad"),
                        Field.ofStringType(departamentoField)
                                .required(true)
                                .label("Departamento")
                )
        );

        mainPane.setCenter(new FormRenderer(form));


        codigoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCodigo())));
        nombreTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        poblacionTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPoblacion())));
        prioridadTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPrioridad())));
        departamentoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getDepartamento())));

        tableView.getItems().clear();
        tableView.setItems(observableList);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarInformacionSeleccionada(newSelection);
            }
        });
        llenarTabla();
    }

    private void llenarTabla() {
        Thread thread = new Thread(() -> {
            List<Municipio> result = service.buscarTodos();
            System.out.println(result);
            observableList.setAll(result);
        });
        Platform.runLater(thread);
    }


    private void mostrarInformacionSeleccionada(Municipio municipio) {
        codigoField.set(String.valueOf(municipio.getCodigo()));
        nombreField.set(municipio.getNombre());
        poblacionField.set(String.valueOf(municipio.getPoblacion()));
        departamentoField.set(String.valueOf(municipio.getDepartamento()));
    }

    @FXML
    void actualizar(ActionEvent event) {
        form.persist();
        Municipio toSave = Municipio.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .nombre(nombreField.get())
                .poblacion(Integer.parseInt(poblacionField.get()))
                .prioridad(Integer.parseInt(prioridadField.get()))
                .departamento(Integer.parseInt(departamentoField.get()))
                .build();
        service.actualizarMunicipio(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Municipio actualizado correctamente").show();
    }

    @FXML
    void crear(ActionEvent event) {
        form.persist();
        Municipio toSave = Municipio.builder()
                .nombre(nombreField.get())
                .poblacion(Integer.parseInt(poblacionField.get()))
                .prioridad(Integer.parseInt(prioridadField.get()))
                .departamento(Integer.parseInt(departamentoField.get()))
                .build();
        service.crearMunicipio(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Municipio creado correctamente").show();
    }

    @FXML
    void eliminar(ActionEvent event) {
        form.persist();
        Municipio toSave = Municipio.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .build();
        try {
            service.eliminarMunicipio(toSave.getCodigo());
            llenarTabla();
            form.reset();
            limpiarDatos();
            new Alert(Alert.AlertType.INFORMATION, "Municipio eliminado correctamente").show();

        } catch (CustomError e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        }
    }
}
