package com.example.dbclientapp.model;

import com.example.dbclientapp.dao.AppointmentsQuery;
import com.example.dbclientapp.dao.CustomersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Customers {

    private static Customer selectedCustomer;

    /**
     * This method gets all customers from the database
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        ResultSet rsCustomers = CustomersQuery.selectAll();
        customers.clear();
        while (rsCustomers.next()) {
            int id = rsCustomers.getInt("Customer_ID");
            String name = rsCustomers.getString("Customer_Name");
            String address = rsCustomers.getString("Address");
            String postal = rsCustomers.getString("Postal_Code");
            String phone = rsCustomers.getString("Phone");
            int division = rsCustomers.getInt("Division_ID");

            Customer customer = new Customer(id, name, address, postal, phone, division);
            customers.add(customer);
        }
        return customers;
    }

    /**
     * This method gets all customer IDs from the database
     */
    public static ObservableList<Integer> getCustomerIds() throws SQLException {
        ObservableList<Integer> customerIds = FXCollections.observableArrayList();
        ResultSet rsCustomers = CustomersQuery.selectAll();
        while (rsCustomers.next()) {
            int id = rsCustomers.getInt("Customer_ID");
            customerIds.add(id);
        }
        return customerIds;
    }

    /**
     * This method sets the selected customer
     */
    public static void setSelectedCustomer(Customer customer) {
        selectedCustomer = customer;
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public static boolean hasAppointment(int customerId) throws SQLException {
        ResultSet rsAppointments = AppointmentsQuery.selectAll();
        while (rsAppointments.next()) {
            if (rsAppointments.getInt("Customer_ID") == customerId) {
                return true;
            }
        }
        return false;
    }

}
