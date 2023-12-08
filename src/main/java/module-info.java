module com.example.dbclientapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.dbclientapp.main to javafx.fxml;
    exports com.example.dbclientapp.main;
    opens com.example.dbclientapp.controller to javafx.fxml;
    exports com.example.dbclientapp.controller;
    opens com.example.dbclientapp.model to javafx.fxml;
    exports com.example.dbclientapp.model;
}