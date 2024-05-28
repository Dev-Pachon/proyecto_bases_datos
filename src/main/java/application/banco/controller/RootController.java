package application.banco.controller;

import application.banco.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class RootController {

    @FXML
    private TabPane mainPane;

    @FXML
    private Tab ayudaTab;

    @FXML
    private Tab entidadesTab;

    @FXML
    private Tab reportesTab;

    @FXML
    private Tab transaccionesTab;

    @FXML
    private Tab utilidadesTab;

    @FXML
    void initialize() {
        transaccionesTab.setContent(getNode("views/transaciones-view.fxml"));
        mainPane.getSelectionModel().select(transaccionesTab);
        transaccionesTab.setOnSelectionChanged(event -> {
            transaccionesTab.setContent(getNode("views/transaciones-view.fxml"));
        });
        entidadesTab.setOnSelectionChanged(event -> {
            entidadesTab.setContent(getNode("views/entidades-view.fxml"));
        });
        reportesTab.setOnSelectionChanged(event -> {
            reportesTab.setContent(getNode("views/reportes-view.fxml"));
        });
        utilidadesTab.setOnSelectionChanged(event -> {
            utilidadesTab.setContent(getNode("views/utilidades-view.fxml"));
        });
        ayudaTab.setOnSelectionChanged(event -> {
            ayudaTab.setContent(getNode("views/ayuda-view.fxml"));
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
