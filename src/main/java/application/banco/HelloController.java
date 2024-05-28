package application.banco;

import application.banco.state.EstadoAplicacion;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Arrays;

public class HelloController {
    @FXML
    private HBox loginStateHbox;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label pageLabel;

    @FXML
    private Label usuarioLabel;
    private EstadoAplicacion estado;

    @FXML
    void initialize() {

        estado = EstadoAplicacion.getInstance();
        estado.setMainPane(mainPane);
        estado.setPageLabel(pageLabel);
        estado.setUsuarioLabel(usuarioLabel);
        estado.setLoginStateHbox(loginStateHbox);
        loginStateHbox.setVisible(false);
        estado.loadPage("views/login-view.fxml", "INICIAR SESION");
    }


    @FXML
    public void cerrarSesion(){
        estado.loadPage("views/login-view.fxml", "INICIAR SESION");
        estado.setUsuario(null);
        loginStateHbox.setVisible(false);
    }

}