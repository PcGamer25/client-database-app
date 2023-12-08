package com.example.dbclientapp.controller;

import com.example.dbclientapp.dao.CustomersQuery;
import com.example.dbclientapp.model.Customer;
import com.example.dbclientapp.model.Customers;
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
import java.util.ResourceBundle;

public class CustomersController implements Initializable {

    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, Integer> customersIdColumn;
    @FXML
    private TableColumn<Customer, String> customersNameColumn;
    @FXML
    private TableColumn<Customer, String> customersPhoneColumn;
    @FXML
    private TableColumn<Customer, Integer> customersDivisionColumn;

    private Parent root;
    private Stage stage;

    /**
     * This method sets up the customers table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customersIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customersNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customersPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customersDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));

        try {
            customersTable.setItems(Customers.getCustomers());
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
     * This method switches to the add customer page
     */
    public void switchToAddCustomer(ActionEvent event) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/AddCustomer.fxml"));
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.stage.setScene(new Scene(this.root));
        this.stage.show();
    }

    /**
     * This method switches to the edit customer page
     */
    public void switchToEditCustomer(ActionEvent event) throws IOException {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Customers.setSelectedCustomer(selectedCustomer);

            this.root = FXMLLoader.load(getClass().getResource("/com/example/dbclientapp/view/EditCustomer.fxml"));
            this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            this.stage.setScene(new Scene(this.root));
            this.stage.show();
        }
    }

    /**
     * This method deletes the customer from the database
     */
    public void delete(ActionEvent event) throws SQLException {
        if (customersTable.getSelectionModel().getSelectedItem() != null) {
            if (!Customers.hasAppointment(customersTable.getSelectionModel().getSelectedItem().getId())) {
                Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDelete.setTitle("Delete Customer");
                confirmDelete.setHeaderText("");
                confirmDelete.setContentText("Are you sure you want to delete this customer?");
                confirmDelete.getDialogPane().setGraphic(null);

                if (confirmDelete.showAndWait().get() == ButtonType.OK) {
                    CustomersQuery.delete(customersTable.getSelectionModel().getSelectedItem().getId());
                    customersTable.setItems(Customers.getCustomers());

                    Alert hasAppointmentError = new Alert(Alert.AlertType.ERROR);
                    hasAppointmentError.setTitle("Delete Customer");
                    hasAppointmentError.setHeaderText("");
                    hasAppointmentError.setContentText("Customer deleted");
                    hasAppointmentError.getDialogPane().setGraphic(null);
                    hasAppointmentError.show();
                }
            } else {
                Alert hasAppointmentError = new Alert(Alert.AlertType.ERROR);
                hasAppointmentError.setTitle("Delete Customer");
                hasAppointmentError.setHeaderText("");
                hasAppointmentError.setContentText("This customer has an associated appointment");
                hasAppointmentError.getDialogPane().setGraphic(null);
                hasAppointmentError.show();
            }
        }
    }

}
