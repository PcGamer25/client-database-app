package com.example.dbclientapp.controller;

import com.example.dbclientapp.dao.CustomersQuery;
import com.example.dbclientapp.dao.DivisionsQuery;
import com.example.dbclientapp.model.Countries;
import com.example.dbclientapp.model.Customer;
import com.example.dbclientapp.model.Customers;
import com.example.dbclientapp.model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable {

    @FXML
    private TextField customerIdField;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private TextField customerPostalField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private ComboBox<String> customerCountryBox;
    @FXML
    private ComboBox<String> customerDivisionBox;

    private Parent root;
    private Stage stage;
    private Customer selectedCustomer;

    /**
     * This method sets up the edit customer form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCustomer = Customers.getSelectedCustomer();

        try {
            customerCountryBox.setItems(Countries.getCountryNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            fillFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
     * This method fills the form with the selected customer information
     */
    public void fillFields() throws SQLException {
        customerIdField.setText(String.valueOf(selectedCustomer.getId()));
        customerNameField.setText(selectedCustomer.getName());
        customerAddressField.setText(selectedCustomer.getAddress());
        customerPostalField.setText(selectedCustomer.getPostal());
        customerPhoneField.setText(selectedCustomer.getPhone());

        customerCountryBox.setValue(Divisions.getCountry(selectedCustomer.getDivision()));
        customerDivisionBox.setValue(Divisions.getDivisionName(selectedCustomer.getDivision()));
        countrySelection(new ActionEvent());
    }

    /**
     * This method sets the items in the division ComboBox
     */
    public void countrySelection(ActionEvent event) throws SQLException {
        customerDivisionBox.setItems(getFilteredDivisions(DivisionsQuery.selectAll()));
    }

    /**
     * This method filters the divisions based on country selection
     */
    public ObservableList<String> getFilteredDivisions(ResultSet rsDivision) throws SQLException {
        ObservableList<String> filteredDivisions = FXCollections.observableArrayList();

        String countrySelected = customerCountryBox.getValue();
        int countrySelectedId = Countries.getCountryId(countrySelected);

        while (rsDivision.next()) {
            if (rsDivision.getInt("Country_ID") == countrySelectedId) {
                filteredDivisions.add(rsDivision.getString("Division"));
            }
        }
        return filteredDivisions;
    }

    /**
     * This method updates the customer in the database
     */
    public void save(ActionEvent event) throws SQLException, IOException {
        int id = selectedCustomer.getId();
        String name = customerNameField.getText();
        String address = customerAddressField.getText();
        String postal = customerPostalField.getText();
        String phone = customerPhoneField.getText();
        int division = Divisions.getDivisionId(customerDivisionBox.getValue());

        if (!(customerNameField.getText().isBlank()
                || customerAddressField.getText().isBlank()
                || customerPostalField.getText().isBlank()
                || customerPhoneField.getText().isBlank()
                || customerDivisionBox.getValue() == null
        )) {
            CustomersQuery.update(id, name, address, postal, phone, division);

            switchToCustomers(event);
        } else {
            Alert fillFieldsError = new Alert(Alert.AlertType.ERROR);
            fillFieldsError.setTitle("Edit Customer");
            fillFieldsError.setHeaderText("");
            fillFieldsError.setContentText("Please fill all fields");
            fillFieldsError.getDialogPane().setGraphic(null);
            fillFieldsError.show();
        }
    }

}
