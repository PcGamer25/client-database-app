package com.example.dbclientapp.model;

import com.example.dbclientapp.dao.ContactsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Contacts {

    /**
     * This method gets all contact names from the database
     */
    public static ObservableList<String> getContactNames() throws SQLException {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        ResultSet rsContacts = ContactsQuery.selectAll();
        while (rsContacts.next()) {
            String name = rsContacts.getString("Contact_Name");
            contactNames.add(name);
        }
        return contactNames;
    }

    /**
     * This method gets the contact name of a contact ID
     */
    public static String getContactName(int contactId) throws SQLException {
        ResultSet rsContacts = ContactsQuery.selectAll();
        while (rsContacts.next()) {
            if (rsContacts.getInt("Contact_ID") == contactId) {
                return rsContacts.getString("Contact_Name");
            }
        }
        return null;
    }

    /**
     * This method gets the contact ID of a contact name
     */
    public static int getContactId(String contactName) throws SQLException {
        ResultSet rsContacts = ContactsQuery.selectAll();
        while (rsContacts.next()) {
            if (rsContacts.getString("Contact_Name").equals(contactName)) {
                return rsContacts.getInt("Contact_ID");
            }
        }
        return -1;
    }

}
