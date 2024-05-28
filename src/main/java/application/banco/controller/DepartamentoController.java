package application.banco.controller;

import application.banco.error.CustomError;
import application.banco.model.Contrato;
import application.banco.model.Departamento;
import application.banco.service.serviceImpl.ContratoService;
import application.banco.service.serviceImpl.DepartamentoService;
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

public class DepartamentoController {

    @FXML
    private Button actualizarBtn;

    @FXML
    private Button crearBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private TableView<Departamento> tableView;

    @FXML
    private TableColumn<Departamento, String> nombreTc;

    @FXML
    private TableColumn<Departamento, String> codigoTc;

    @FXML
    private TableColumn<Departamento, String> poblacionTc;

    @FXML
    private BorderPane mainPane;

    private Form form;

    private StringProperty codigoField;
    private StringProperty nombreField;
    private IntegerProperty poblacionField;

    private EstadoAplicacion estadoAplicacion;

    private DepartamentoService service;

    ObservableList<Departamento> departamentosObservableList = FXCollections.observableArrayList();


    public void limpiarDatos() {
        codigoField.set("");
        nombreField.set("");
        poblacionField.set(0);
    }

    @FXML
    void initialize() {
        estadoAplicacion = EstadoAplicacion.getInstance();

        service = new DepartamentoService();

        NivelService nivel = new NivelService();

        codigoField = new SimpleStringProperty("");
        nombreField = new SimpleStringProperty("");
        poblacionField = new SimpleIntegerProperty(0);

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
                        Field.ofIntegerType(poblacionField)
                                .id("poblacion")
                                .required(true)
                                .label("Poblacion")
                )
        );

        mainPane.setCenter(new FormRenderer(form));


        codigoTc.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCodigo())));
        nombreTc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        poblacionTc.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPoblacion())));

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
            List<Departamento> result = service.buscarTodos();
            System.out.println(result);
            departamentosObservableList.setAll(result);
        });
        Platform.runLater(thread);
    }


    private void mostrarInformacionContrato(Departamento departamento) {
        codigoField.set(String.valueOf(departamento.getCodigo()));
        nombreField.set(String.valueOf(departamento.getNombre()));
        poblacionField.set(departamento.getPoblacion());
    }

    @FXML
    void actualizar(ActionEvent event) {
        form.persist();
        Departamento toSave = Departamento.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .nombre(nombreField.get())
                .poblacion(poblacionField.get())
                .build();
        service.actualizarDepartamento(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Departamento actualizado correctamente").show();
    }

    @FXML
    void crear(ActionEvent event) {
        form.persist();
        Departamento toSave = Departamento.builder()
                .nombre(nombreField.get())
                .poblacion(poblacionField.get())
                .build();

        service.crearDepartamento(toSave);
        llenarTabla();
        form.reset();
        limpiarDatos();
        new Alert(Alert.AlertType.INFORMATION, "Departamento creado correctamente").show();
    }

    @FXML
    void eliminar(ActionEvent event) {
        form.persist();
        Departamento toSave = Departamento.builder()
                .codigo(Integer.parseInt(codigoField.get()))
                .build();
        try {
            service.eliminarDepartamento(toSave.getCodigo());
            llenarTabla();
            form.reset();
            limpiarDatos();
            new Alert(Alert.AlertType.INFORMATION, "Departamento eliminado correctamente").show();

        } catch (CustomError e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        }
    }
}
