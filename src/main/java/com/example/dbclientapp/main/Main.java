package com.example.dbclientapp.main;

import com.example.dbclientapp.dao.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

/**
 * @author David Huang
 */
public class Main extends Application {

    /**
     * This method sets up and loads the login page
     */
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/Login.fxml"));

        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * This method launches the application
     */
    public static void main(String[] args) throws SQLException {

        JDBC.openConnection();

        //Run the login page in French if desired
        //Locale.setDefault(new Locale("fr"));

        launch(args);

        JDBC.closeConnection();
    }

}