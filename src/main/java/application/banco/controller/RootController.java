package application.banco.controller;

import application.banco.HelloApplication;
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
        entidadesTab.setContent(getNode("views/transaciones-view.fxml"));
        reportesTab.setContent(getNode("views/transaciones-view.fxml"));
        utilidadesTab.setContent(getNode("views/transaciones-view.fxml"));
        ayudaTab.setContent(getNode("views/transaciones-view.fxml"));
        mainPane.getSelectionModel().clearAndSelect(2);
    }

    private Node getNode(String pageURI) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(pageURI));
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
