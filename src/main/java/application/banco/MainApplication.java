package application.banco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/root-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinHeight(800);
        stage.setMinWidth(1280);
        stage.setTitle("Banco Centenario");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}