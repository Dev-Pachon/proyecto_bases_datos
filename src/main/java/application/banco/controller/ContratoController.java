package application.banco.controller;

import application.banco.state.EstadoAplicacion;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ContratoController {


    @FXML
    private Button actualizarBtn;

    @FXML
    private Button crearBtn;

    @FXML
    private Button eliminarBtn;

    @FXML
    private TableView<?> tableView;

    @FXML
    private BorderPane mainPane;

    private Form form;

    private StringProperty usuarioField;

    private StringProperty claveField;

    EstadoAplicacion estadoAplicacion;

    @FXML
    void initialize() {
        estadoAplicacion = EstadoAplicacion.getInstance();

        usuarioField = new SimpleStringProperty("");
        claveField = new SimpleStringProperty("");

        form = Form.of(
                Group.of(Field.ofStringType(usuarioField)
                                .id("usuario")
                                .label("Usuario")
                                .required("El usuario es requerido"),
                        Field.ofPasswordType(claveField)
                                .id("clave")
                                .label("Clave")
                                .required("La clave es requerida")
                )
        ).title("Inicio de Sesion");

        mainPane.setCenter(new FormRenderer(form));
    }

}
