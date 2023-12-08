package com.example.dbclientapp.controller;

import com.example.dbclientapp.model.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label locationLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;

    private Parent root;
    private Stage stage;
    private String locale;

    /**
     * This method sets up the login form in the proper language
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locale = Locale.getDefault().toString();

        if (locale.equals("fr")) {
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            loginButton.setText("Se Connecter");
            locationLabel.setText("Localisation: " + ZoneId.systemDefault());
        } else {
            locationLabel.setText("Location: " + ZoneId.systemDefault());
        }
    }

    /**
     * This method authenticates a username and password
     */
    public void authenticate(ActionEvent event) throws SQLException, IOException {
        String userName = userNameField.getText();
        String password = passwordField.getText();

        if (Users.authenticate(userName, password)) {
            logSuccess();
            switchToDirectory(event);
        } else if (locale.equals("fr")) {
            logFailed();
            Alert loginError = new Alert(Alert.AlertType.ERROR);
            loginError.setTitle("Se Connecter");
            loginError.setHeaderText("");
            loginError.setContentText("L'identifiant ou le mot de passe est incorrect");
            loginError.getDialogPane().setGraphic(null);
            loginError.show();
        } else {
            logFailed();
            Alert loginError = new Alert(Alert.AlertType.ERROR);
            loginError.setTitle("Log In");
            loginError.setHeaderText("");
            loginError.setContentText("Username or password is incorrect");
            loginError.getDialogPane().setGraphic(null);
            loginError.show();
        }
    }

    /**
     * This method switches to the directory page
     */
    public void switchToDirectory(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/Directory.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method records a successful login to the log
     */
    public void logSuccess() throws IOException {
        FileWriter logger = new FileWriter("login_activity.txt", true);
        logger.write("User " + userNameField.getText() + " logged in at " + LocalTime.now() + " on " + LocalDate.now() + "\n");
        logger.close();
    }

    /**
     * This method records a failed login to the log
     */
    public void logFailed() throws IOException {
        FileWriter logger = new FileWriter("login_activity.txt", true);
        logger.write("User " + userNameField.getText() + " failed to log in at " + LocalTime.now() + " on " + LocalDate.now() + "\n");
        logger.close();
    }

}