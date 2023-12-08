package com.example.dbclientapp.controller;

import com.example.dbclientapp.dao.AppointmentsQuery;
import com.example.dbclientapp.helper.BusinessHoursChecker;
import com.example.dbclientapp.helper.TimeConflictChecker;
import com.example.dbclientapp.helper.ZDTToTimestamp;
import com.example.dbclientapp.model.Contacts;
import com.example.dbclientapp.model.Customers;
import com.example.dbclientapp.model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private TextField appointmentTitleField;
    @FXML
    private TextField appointmentDescriptionField;
    @FXML
    private TextField appointmentLocationField;
    @FXML
    private TextField appointmentTypeField;
    @FXML
    private DatePicker appointmentStartDatePicker;
    @FXML
    private Spinner<Integer> appointmentStartTimeHoursSpinner;
    @FXML
    private Spinner<String> appointmentStartTimeMinsSpinner;
    @FXML
    private Spinner<String> appointmentStartTimeAmPmSpinner;
    @FXML
    private DatePicker appointmentEndDatePicker;
    @FXML
    private Spinner<Integer> appointmentEndTimeHoursSpinner;
    @FXML
    private Spinner<String> appointmentEndTimeMinsSpinner;
    @FXML
    private Spinner<String> appointmentEndTimeAmPmSpinner;
    @FXML
    private ComboBox<Integer> appointmentCustomerIdBox;
    @FXML
    private ComboBox<Integer> appointmentUserIdBox;
    @FXML
    private ComboBox<String> appointmentContactBox;

    private Parent root;
    private Stage stage;

    /**
     * This method sets up the add appointment form
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentStartDatePicker.setValue(LocalDate.now());
        appointmentEndDatePicker.setValue(LocalDate.now());

        ObservableList<String> mins = FXCollections.observableArrayList();
        mins.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59");

        SpinnerValueFactory<Integer> startTimeHoursValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 12);
        startTimeHoursValueFactory.setWrapAround(true);
        appointmentStartTimeHoursSpinner.setValueFactory(startTimeHoursValueFactory);

        SpinnerValueFactory<String> startTimeMinsValueFactory =
                new SpinnerValueFactory.ListSpinnerValueFactory<>(mins);
        startTimeMinsValueFactory.setWrapAround(true);
        appointmentStartTimeMinsSpinner.setValueFactory(startTimeMinsValueFactory);

        SpinnerValueFactory<Integer> endTimeHoursValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 12);
        endTimeHoursValueFactory.setWrapAround(true);
        appointmentEndTimeHoursSpinner.setValueFactory(endTimeHoursValueFactory);

        SpinnerValueFactory<String> endTimeMinsValueFactory =
                new SpinnerValueFactory.ListSpinnerValueFactory<>(mins);
        endTimeMinsValueFactory.setWrapAround(true);
        appointmentEndTimeMinsSpinner.setValueFactory(endTimeMinsValueFactory);

        ObservableList<String> amPm = FXCollections.observableArrayList();
        amPm.addAll("PM", "AM");

        SpinnerValueFactory<String> startTimeAmPmValueFactory =
                new SpinnerValueFactory.ListSpinnerValueFactory<>(amPm);
        startTimeAmPmValueFactory.setWrapAround(true);
        appointmentStartTimeAmPmSpinner.setValueFactory(startTimeAmPmValueFactory);

        SpinnerValueFactory<String> endTimeAmPmValueFactory =
                new SpinnerValueFactory.ListSpinnerValueFactory<>(amPm);
        endTimeAmPmValueFactory.setValue("PM");
        endTimeAmPmValueFactory.setWrapAround(true);
        appointmentEndTimeAmPmSpinner.setValueFactory(endTimeAmPmValueFactory);

        try {
            appointmentCustomerIdBox.setItems(Customers.getCustomerIds());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            appointmentUserIdBox.setItems(Users.getUserIds());
            appointmentUserIdBox.setValue(Users.getCurrentUserId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            appointmentContactBox.setItems(Contacts.getContactNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
     * This method verifies all data entered on the form
     */
    public boolean validateData() throws SQLException {
        if (appointmentTitleField.getText().isBlank()
                || appointmentDescriptionField.getText().isBlank()
                || appointmentLocationField.getText().isBlank()
                || appointmentTypeField.getText().isBlank()
                || appointmentCustomerIdBox.getValue() == null
                || appointmentContactBox.getValue() == null
        ) {
            errorMessage("Please fill all fields");
            return false;
        }

        ZonedDateTime startUtcZDT = utcDateTimeCreator(appointmentStartDatePicker, appointmentStartTimeHoursSpinner, appointmentStartTimeMinsSpinner, appointmentStartTimeAmPmSpinner);
        ZonedDateTime endUtcZDT = utcDateTimeCreator(appointmentEndDatePicker, appointmentEndTimeHoursSpinner, appointmentEndTimeMinsSpinner, appointmentEndTimeAmPmSpinner);
        if (startUtcZDT.isAfter(endUtcZDT)) {
            errorMessage("Appointment start time is after end time");
            return false;
        }
        if (!(BusinessHoursChecker.checkHours(startUtcZDT) && BusinessHoursChecker.checkHours(endUtcZDT))) {
            errorMessage("Appointment is outside of business hours 8:00am - 10:00pm ET");
            return false;
        }
        if (!TimeConflictChecker.checkConflict(startUtcZDT, endUtcZDT, appointmentCustomerIdBox.getValue())) {
            errorMessage("The customer has another appointment scheduled at this time");
            return false;
        }
        return true;
    }

    /**
     * This method displays a custom error message
     */
    public void errorMessage(String message) {
        Alert outsideBusinessHoursError = new Alert(Alert.AlertType.ERROR);
        outsideBusinessHoursError.setTitle("Add Appointment");
        outsideBusinessHoursError.setHeaderText("");
        outsideBusinessHoursError.setContentText(message);
        outsideBusinessHoursError.getDialogPane().setGraphic(null);
        outsideBusinessHoursError.show();
    }

    /**
     * This method adds the appointment to the database
     */
    public void save(ActionEvent event) throws SQLException, IOException {

        if (validateData()) {
            String title = appointmentTitleField.getText();
            String description = appointmentDescriptionField.getText();
            String location = appointmentLocationField.getText();
            String type = appointmentTypeField.getText();
            int customerId = appointmentCustomerIdBox.getValue();
            int userId = appointmentUserIdBox.getValue();
            int contactId = Contacts.getContactId(appointmentContactBox.getValue());

            ZonedDateTime startUtcZDT = utcDateTimeCreator(appointmentStartDatePicker, appointmentStartTimeHoursSpinner, appointmentStartTimeMinsSpinner, appointmentStartTimeAmPmSpinner);
            ZonedDateTime endUtcZDT = utcDateTimeCreator(appointmentEndDatePicker, appointmentEndTimeHoursSpinner, appointmentEndTimeMinsSpinner, appointmentEndTimeAmPmSpinner);
            Timestamp start = ZDTToTimestamp.toTimestamp(startUtcZDT);
            Timestamp end = ZDTToTimestamp.toTimestamp(endUtcZDT);

            AppointmentsQuery.insert(title, description, location, type, start, end, customerId, userId, contactId);
            switchToAppointments(event);
        }
    }

    /**
     * This method creates a ZonedDateTime object from the form input
     */
    public ZonedDateTime utcDateTimeCreator(DatePicker localDate, Spinner<Integer> hours, Spinner<String> mins, Spinner<String> amPm) {
        int minsInteger = Integer.parseInt(mins.getValue());
        int hoursInteger = hours.getValue();
        if ((hoursInteger != 12) && (amPm.getValue().equals("PM"))) {
            hoursInteger += 12;
        }
        if ((hoursInteger == 12) && (amPm.getValue().equals("AM"))) {
            hoursInteger = 0;
        }
        LocalTime localTime = LocalTime.of(hoursInteger, minsInteger);
        ZonedDateTime localZonedDateTime = ZonedDateTime.of(localDate.getValue(), localTime, ZoneId.systemDefault());
        return ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
    }

}
