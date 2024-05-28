package application.banco.controller;


import application.banco.model.Nivel;
import application.banco.model.Usuario;
import application.banco.service.IUsuarioService;
import application.banco.service.serviceImpl.NivelService;
import application.banco.service.serviceImpl.UsuarioService;
import application.banco.state.EstadoAplicacion;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.util.List;


public class RegistroController {

    @FXML
    private BorderPane mainPane;

    private Form form;

    private StringProperty usuarioField;

    private StringProperty claveField;

    private SingleSelectionField<Nivel> nivel;

    EstadoAplicacion estado;

    @FXML
    void initialize() {

        NivelService nivelService = new NivelService();

        List<Nivel> niveles = nivelService.buscarTodos();

        estado = EstadoAplicacion.getInstance();

        usuarioField = new SimpleStringProperty("");
        claveField = new SimpleStringProperty("");

        nivel = Field.ofSingleSelectionType(niveles, 1)
                .id("nivel")
                .label("Confirmar clave")
                .required("La confirmacion de la clave es requerida");

        form = Form.of(
                Group.of(Field.ofStringType(usuarioField)
                                .id("usuario")
                                .label("Usuario")
                                .required("El usuario es requerido"),
                        Field.ofPasswordType(claveField)
                                .id("clave")
                                .label("Clave")
                                .required("La clave es requerida"),
                        Field.ofPasswordType(claveField)
                                .id("confirmar-clave")
                                .label("Confirmar clave")
                                .required("La confirmacion de la clave es requerida"),
                        Field.ofSingleSelectionType(niveles, 1)
                                .id("nivel")
                                .label("Confirmar clave")
                                .required("La confirmacion de la clave es requerida")
                )
        ).title("Registro");

        mainPane.setCenter(new FormRenderer(form));
    }

    @FXML

    public void onClickCancelar(ActionEvent actionEvent) {
        estado.loadPage("views/login-view.fxml", "INICIAR SESION");
    }

    @FXML
    void onClickRegistrarse(ActionEvent event) {
        if (!form.isValid()) {
            new Alert(Alert.AlertType.ERROR, "Error al iniciar sesión, Usuario o Contrasena invalidos").show();
            return;
        }
        form.persist();
        Usuario usuario = Usuario.builder()
                .nomUsuario(usuarioField.get())
                .clave(claveField.get())
                .nivel(nivel.getSelection().getCodigo())
                .build();
        System.out.println(usuarioService.registrar(usuario));
        form.reset();
        new Alert(Alert.AlertType.INFORMATION, "Usuario registrado correctamente").show();
    }


    private final IUsuarioService usuarioService;

    public RegistroController() {
        this.usuarioService = new UsuarioService();
    }

    public void buscarTodos() {
        usuarioService.buscarTodos().forEach(System.out::println);
    }

    public void buscarPorId() {
        System.out.println(usuarioService.buscarPorId(1));
    }


}
