module application.proyecto_bases_datos {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.proyecto_bases_datos to javafx.fxml;
    exports application.proyecto_bases_datos;
}