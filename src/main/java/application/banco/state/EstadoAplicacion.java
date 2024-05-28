package application.banco.state;

import application.banco.MainApplication;
import application.banco.model.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
public class EstadoAplicacion {

    private static EstadoAplicacion eInstanceState;

    private Label pageLabel;

    private Usuario usuario;

    private BorderPane mainPane;

    private Label usuarioLabel;

    private HBox loginStateHbox;

    public static EstadoAplicacion getInstance() {
        if (eInstanceState == null) {
            eInstanceState = new EstadoAplicacion();
        }

        return eInstanceState;
    }

    private EstadoAplicacion() {

    }

    public void loadPage(String pageURI, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(pageURI));
            mainPane.setCenter(fxmlLoader.load());
            pageLabel.setText(title);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
