package application.banco.controller;

import application.banco.dto.Reporte;
import application.banco.dto.ReporteEmpleadoContratoDTO;
import application.banco.repository.repositoryImpl.ReportesRepository;
import application.banco.state.EstadoAplicacion;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Date;
import java.util.List;

public class ReportesController {

    @FXML
    private TableColumn<ReporteEmpleadoContratoDTO, Integer> c1;

    @FXML
    private TableColumn<ReporteEmpleadoContratoDTO, String> c2;

    @FXML
    private TableColumn<ReporteEmpleadoContratoDTO, Integer> c3;

    @FXML
    private TableColumn<ReporteEmpleadoContratoDTO, String> c4;

    @FXML
    private TableColumn<ReporteEmpleadoContratoDTO, Date> c5;

    @FXML
    private TableColumn<ReporteEmpleadoContratoDTO, Date> c6;

    @FXML
    private TableColumn<ReporteEmpleadoContratoDTO, Double> c7;

    @FXML
    private TableView<ReporteEmpleadoContratoDTO> table;


    @FXML
    void initialize() {

        EstadoAplicacion estadoAplicacion = EstadoAplicacion.getInstance();

        c1.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmpleadoCodigo()).asObject());
        c2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreEmpleado()));
        c3.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getContratoCodigo()).asObject());
        c4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCargo()));
        c5.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getFechaInicio()));
        c6.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getFechaFin()));
        c7.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSalario()).asObject());

        ReportesRepository reportesRepository = new ReportesRepository();

        ObservableList<ReporteEmpleadoContratoDTO> lista = FXCollections.observableArrayList();

        List<Reporte> resultado = reportesRepository.findAllbyID(estadoAplicacion.getUsuario().getNivel());


        resultado.forEach(element -> {
            if (element instanceof ReporteEmpleadoContratoDTO) {
                lista.add((ReporteEmpleadoContratoDTO) element);
            }
        });


        table.getItems().clear();
        table.setItems(lista);
    }

}
