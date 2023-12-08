package com.example.dbclientapp.controller;

import com.example.dbclientapp.dao.AppointmentsQuery;
import com.example.dbclientapp.model.Appointment;
import com.example.dbclientapp.model.Appointments;
import com.example.dbclientapp.model.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentsIdColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentsTitleColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentsDescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentsLocationColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentsContactColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentsTypeColumn;
    @FXML
    private TableColumn<Appointment, ZonedDateTime> appointmentsStartColumn;
    @FXML
    private TableColumn<Appointment, ZonedDateTime> appointmentsEndColumn;
    @FXML
    private TableColumn<Appointment, Integer> appointmentsCustomerIdColumn;
    @FXML
    private TableColumn<Appointment, Integer> appointmentsUserIdColumn;

    private Parent root;
    private Stage stage;

    /**
     * This method sets up the appointments table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentsTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentsLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentsContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentsTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentsStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentsEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentsCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentsUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        try {
            selectAllTimeframe(new ActionEvent());
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
     * This method switches to the add appointment page
     */
    public void switchToAddAppointment(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/AddAppointment.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method switches to the edit appointment page
     */
    public void switchToEditAppointment(ActionEvent event) throws IOException {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Appointments.setSelectedAppointment(selectedAppointment);

            this.root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/EditAppointment.fxml"));
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            this.stage.setScene(new Scene(this.root));
            this.stage.show();
        }
    }

    /**
     * This method gets all appointments for the current user in system date and time
     */
    public ObservableList<Appointment> getAppointmentsSystemTime() throws SQLException {
        ObservableList<Appointment> allAppointmentsSystemTime = Appointments.getAppointments(Users.getCurrentUserId());

        for (Appointment appointment : allAppointmentsSystemTime) {
            ZonedDateTime systemStartDateTime = ZonedDateTime.ofInstant(appointment.getStart().toInstant(), ZoneId.systemDefault());
            appointment.setStart(systemStartDateTime);
            ZonedDateTime systemEndDateTime = ZonedDateTime.ofInstant(appointment.getEnd().toInstant(), ZoneId.systemDefault());
            appointment.setEnd(systemEndDateTime);
        }
        return allAppointmentsSystemTime;
    }

    /**
     * This method sets the view to all appointments
     */
    public void selectAllTimeframe(ActionEvent event) throws SQLException {
        appointmentsTable.setItems(getAppointmentsSystemTime());
    }

    /**
     * This method sets the view to appointments within the current week (Monday-Sunday)
     */
    public void selectWeekTimeframe(ActionEvent event) throws SQLException {
        appointmentsTable.getItems().clear();
        ObservableList<Appointment> allAppointmentsSystemTime = getAppointmentsSystemTime();

        LocalDate startOfWeek = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        LocalDate startOfNextWeek = startOfWeek.plusWeeks(1);

        for (Appointment appointment : allAppointmentsSystemTime) {
            if ((appointment.getStart().toLocalDate().isEqual(startOfWeek) || appointment.getStart().toLocalDate().isAfter(startOfWeek))
                    && appointment.getStart().toLocalDate().isBefore(startOfNextWeek)) {
                appointmentsTable.getItems().add(appointment);
            }
        }
    }

    /**
     * This method sets the view to appointments within the current month
     */
    public void selectMonthTimeframe(ActionEvent event) throws SQLException {
        appointmentsTable.getItems().clear();
        ObservableList<Appointment> allAppointmentsSystemTime = getAppointmentsSystemTime();

        int currentYear = ZonedDateTime.now().getYear();
        int currentMonth = ZonedDateTime.now().getMonthValue();
        for (Appointment appointment : allAppointmentsSystemTime) {
            if (appointment.getStart().getYear() == currentYear
                    && appointment.getStart().getMonthValue() == currentMonth) {
                appointmentsTable.getItems().add(appointment);
            }
        }
    }

    /**
     * This method deletes the appointment from the database
     */
    public void delete(ActionEvent event) throws SQLException {
        if (appointmentsTable.getSelectionModel().getSelectedItem() != null) {
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setTitle("Delete Appointment");
            confirmDelete.setHeaderText("");
            confirmDelete.setContentText("Are you sure you want to delete this appointment?");
            confirmDelete.getDialogPane().setGraphic(null);

            if (confirmDelete.showAndWait().get() == ButtonType.OK) {
                Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
                AppointmentsQuery.delete(selectedAppointment.getId());
                appointmentsTable.setItems(getAppointmentsSystemTime());

                Alert hasAppointmentError = new Alert(Alert.AlertType.ERROR);
                hasAppointmentError.setTitle("Delete Appointment");
                hasAppointmentError.setHeaderText("");
                hasAppointmentError.setContentText("Appointment ID " + selectedAppointment.getId() + " " + selectedAppointment.getType() + " deleted");
                hasAppointmentError.getDialogPane().setGraphic(null);
                hasAppointmentError.show();
            }
        }
    }

}
