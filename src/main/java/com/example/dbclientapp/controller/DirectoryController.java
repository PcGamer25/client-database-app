package com.example.dbclientapp.controller;

import com.example.dbclientapp.model.Appointment;
import com.example.dbclientapp.model.Appointments;
import com.example.dbclientapp.model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class DirectoryController implements Initializable {

    private Parent root;
    private Stage stage;

    /**
     * This method alerts the user if there is an upcoming appointment
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentAlert();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method switches to the login page
     */
    public void switchToLogin(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/Login.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method switches to the appointments page
     */
    public void switchToAppointments(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/Appointments.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method switches to the customers page
     */
    public void switchToCustomers(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/Customers.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method switches to the reports page
     */
    public void switchToReports(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/Reports.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method adds appointments to the alert message
     */
    public void appointmentAlert() throws SQLException {
        ObservableList<Appointment> appointments = Appointments.getAppointments(Users.getCurrentUserId());
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : appointments) {
            ZonedDateTime localZDT = ZonedDateTime.ofInstant(appointment.getStart().toInstant(), ZoneId.systemDefault());

            if (ChronoUnit.MINUTES.between(ZonedDateTime.now(), localZDT) >= 0
                    && ChronoUnit.MINUTES.between(ZonedDateTime.now(), localZDT) < 15) {
                upcomingAppointments.add(appointment);
            }
        }

        if (!upcomingAppointments.isEmpty()) {
            String alertMessage = "Upcoming appointments within 15 minutes:\n";
            for (Appointment appointment : upcomingAppointments) {
                ZonedDateTime localZDT = ZonedDateTime.ofInstant(appointment.getStart().toInstant(), ZoneId.systemDefault());
                LocalDate localDate = localZDT.toLocalDate();
                LocalTime localTime = localZDT.toLocalTime();
                alertMessage += "Appointment ID " + appointment.getId() + " at " + localTime + " on " + localDate + "\n";
            }
            appointmentAlertMessage(alertMessage);
        } else {
            appointmentAlertMessage("There are no upcoming appointments within 15 minutes");
        }

    }

    /**
     * This method displays a custom alert message
     */
    public void appointmentAlertMessage(String message) {
        Alert outsideBusinessHoursError = new Alert(Alert.AlertType.ERROR);
        outsideBusinessHoursError.setTitle("Appointments");
        outsideBusinessHoursError.setHeaderText("");
        outsideBusinessHoursError.setContentText(message);
        outsideBusinessHoursError.getDialogPane().setGraphic(null);
        outsideBusinessHoursError.show();
    }

}