module application.banco {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires static lombok;
    requires java.sql;
    requires org.apache.commons.codec;
    requires mysql.connector.j;

    opens application.banco to javafx.fxml;
    opens application.banco.controller to javafx.fxml;
    exports application.banco;
    exports application.banco.controller;
}