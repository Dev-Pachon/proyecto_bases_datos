package application.banco.controller;

import application.banco.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class EntidadesController {

    @FXML
    private Tab CargosTab;

    @FXML
    private Tab EmpleadosTab;

    @FXML
    private Tab MunicipioTab;

    @FXML
    private Tab ProfesionalesTab;

    @FXML
    private Tab SucursalTab;

    @FXML
    private Tab departamentoTab;

    @FXML
    private TabPane mainPane;

    @FXML
    private Tab tipoMunicipioTab;

    @FXML
    void initialize() {
        departamentoTab.setContent(getNode("views/entidades/departamento-view.fxml"));
        mainPane.getSelectionModel().select(departamentoTab);
        departamentoTab.setOnSelectionChanged(event -> {
            departamentoTab.setContent(getNode("views/entidades/departamento-view.fxml"));
        });
        EmpleadosTab.setOnSelectionChanged(event -> {
            EmpleadosTab.setContent(getNode("views/entidades/empleados-view.fxml"));
        });
        CargosTab.setOnSelectionChanged(event -> {
            CargosTab.setContent(getNode("views/entidades/cargos-view.fxml"));
        });
        MunicipioTab.setOnSelectionChanged(event -> {
            MunicipioTab.setContent(getNode("views/entidades/municipio-view.fxml"));
        });
        ProfesionalesTab.setOnSelectionChanged(event -> {
            ProfesionalesTab.setContent(getNode("views/entidades/profesionales-view.fxml"));
        });
        SucursalTab.setOnSelectionChanged(event -> {
            SucursalTab.setContent(getNode("views/entidades/sucursal-view.fxml"));
        });
        tipoMunicipioTab.setOnSelectionChanged(event -> {
            tipoMunicipioTab.setContent(getNode("views/entidades/tipo_municipio-view.fxml"));
        });
    }

    private Node getNode(String pageURI) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(pageURI));
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
