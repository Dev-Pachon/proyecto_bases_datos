package application.banco;

import application.banco.state.EstadoAplicacion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainController {
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