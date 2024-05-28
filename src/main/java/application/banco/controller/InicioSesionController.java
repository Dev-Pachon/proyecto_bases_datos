package application.banco.controller;


import application.banco.model.Usuario;
import application.banco.service.IUsuarioService;
import application.banco.service.serviceImpl.UsuarioService;
import application.banco.state.EstadoAplicacion;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;


public class InicioSesionController {

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

    @FXML
    void onClickIniciarSesion(ActionEvent event) {
        if (!form.isValid()) {
            new Alert(Alert.AlertType.ERROR, "Error al iniciar sesión, Usuario o Contrasena invalidos").show();
            return;
        }
        form.persist();
        Usuario usuario = usuarioService.inicioSesion(usuarioField.get(), claveField.get());
        if (usuario == null) {
            form.reset();
            new Alert(Alert.AlertType.ERROR, "Error al iniciar sesión, Usuario o Contrasena invalidos").show();
            return;
        }
        estadoAplicacion.setUsuario(usuario);
        estadoAplicacion.getLoginStateHbox().setVisible(true);
        estadoAplicacion.getUsuarioLabel().setText(usuario.getNomUsuario());
        estadoAplicacion.loadPage("views/main-view.fxml", "MENU PRINCIPAL");

    }

    @FXML
    void onClickRegistrarse(ActionEvent event) {
        estadoAplicacion.loadPage("views/registro-view.fxml", "REGISTRO");
    }


    private final IUsuarioService usuarioService;

    public InicioSesionController() {
        this.usuarioService = new UsuarioService();
    }

    public void buscarTodos() {
        usuarioService.buscarTodos().forEach(System.out::println);
    }

    public void buscarPorId() {
        System.out.println(usuarioService.buscarPorId(1));
    }
}
