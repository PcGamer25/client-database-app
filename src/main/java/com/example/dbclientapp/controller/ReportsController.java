package com.example.dbclientapp.controller;

import com.example.dbclientapp.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class ReportsController implements Initializable {

    @FXML
    private ComboBox<Month> monthBox;
    @FXML
    private ComboBox<String> typeBox;
    @FXML
    private TextField countField;
    @FXML
    private ComboBox<String> contactBox;
    @FXML
    private ComboBox<Integer> customerBox;
    @FXML
    private ComboBox<Integer> userBox;
    @FXML
    private TableView<Appointment> schedulesTable;
    @FXML
    private TableColumn<Appointment, Integer> schedulesIdColumn;
    @FXML
    private TableColumn<Appointment, String> schedulesTitleColumn;
    @FXML
    private TableColumn<Appointment, String> schedulesTypeColumn;
    @FXML
    private TableColumn<Appointment, String> schedulesDescriptionColumn;
    @FXML
    private TableColumn<Appointment, ZonedDateTime> schedulesStartColumn;
    @FXML
    private TableColumn<Appointment, ZonedDateTime> schedulesEndColumn;
    @FXML
    private TableColumn<Appointment, Integer> schedulesCustomerIdColumn;

    private Parent root;
    private Stage stage;
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * This method sets up the reports form and table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allAppointments.addAll(Appointments.getAppointments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int month = 1; month <= 12; month++) {
            monthBox.getItems().add(Month.of(month));
        }

        for (Appointment appointment : allAppointments) {
            if (!typeBox.getItems().contains(appointment.getType())) {
                typeBox.getItems().add(appointment.getType());
            }
        }

        try {
            contactBox.setItems(Contacts.getContactNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            customerBox.setItems(Customers.getCustomerIds());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            userBox.setItems(Users.getUserIds());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        schedulesIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        schedulesTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        schedulesTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        schedulesDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        schedulesStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        schedulesEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        schedulesCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
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
     * This method switches to the reports page
     */
    public void switchToReports(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/Reports.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method contains a lambda expression that updates the count display for appointments found
     */
    public void showCount(ActionEvent event) {
        if (monthBox.getSelectionModel().getSelectedItem() != null
                && typeBox.getSelectionModel().getSelectedItem() != null) {
            Month selectedMonth = monthBox.getSelectionModel().getSelectedItem();
            String selectedType = typeBox.getSelectionModel().getSelectedItem();

            AtomicInteger count = new AtomicInteger();
            allAppointments.forEach(x -> {
                if (x.getStart().getMonth().equals(selectedMonth)
                        && x.getType().equals(selectedType)) {
                    count.getAndIncrement();
                }
            });
            countField.setText("Count: " + count);
        }
    }

    /**
     * This method contains three lambda expressions that update the reports table for appointments found
     */
    public void showSchedules(ActionEvent event) throws SQLException {
        if (contactBox.getSelectionModel().getSelectedItem() != null) {
            customerBox.setDisable(true);
            userBox.setDisable(true);

            schedulesTable.getItems().clear();
            allAppointments.forEach(x -> {
                if (x.getContactName().equals(contactBox.getSelectionModel().getSelectedItem())) {
                    schedulesTable.getItems().add(x);
                }
            });
        }
        if (customerBox.getSelectionModel().getSelectedItem() != null) {
            contactBox.setDisable(true);
            userBox.setDisable(true);

            schedulesTable.getItems().clear();
            allAppointments.forEach(x -> {
                if (x.getCustomerId() == customerBox.getSelectionModel().getSelectedItem()) {
                    schedulesTable.getItems().add(x);
                }
            });
        }
        if (userBox.getSelectionModel().getSelectedItem() != null) {
            contactBox.setDisable(true);
            customerBox.setDisable(true);

            schedulesTable.getItems().clear();
            allAppointments.forEach(x -> {
                if (x.getUserId() == userBox.getSelectionModel().getSelectedItem()) {
                    schedulesTable.getItems().add(x);
                }
            });
        }
    }

}
