package com.example.dbclientapp.model;

import com.example.dbclientapp.dao.AppointmentsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class Appointments {

    private static Appointment selectedAppointment;

    /**
     * This method gets all appointments from the database
     */
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        ResultSet rsAppointments = AppointmentsQuery.selectAll();
        while (rsAppointments.next()) {
            int id = rsAppointments.getInt("Appointment_ID");
            String title = rsAppointments.getString("Title");
            String description = rsAppointments.getString("Description");
            String location = rsAppointments.getString("Location");
            String type = rsAppointments.getString("Type");

            Instant startInstant = rsAppointments.getTimestamp("Start").toInstant();
            ZonedDateTime startDateTime = ZonedDateTime.ofInstant(startInstant, ZoneId.of("UTC"));
            Instant endInstant = rsAppointments.getTimestamp("End").toInstant();
            ZonedDateTime endDateTime = ZonedDateTime.ofInstant(endInstant, ZoneId.of("UTC"));

            int customerId = rsAppointments.getInt("Customer_ID");
            int userId = rsAppointments.getInt("User_ID");
            int contactId = rsAppointments.getInt("Contact_ID");
            String contactName = Contacts.getContactName(contactId);

            appointments.add(new Appointment(id, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId, contactName));
        }
        return appointments;
    }

    /**
     * This method gets all appointments of a specified user from the database
     */
    public static ObservableList<Appointment> getAppointments(int loggedInUserId) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        ResultSet rsAppointments = AppointmentsQuery.selectAll();
        while (rsAppointments.next()) {
            if (rsAppointments.getInt("User_ID") == loggedInUserId) {
                int id = rsAppointments.getInt("Appointment_ID");
                String title = rsAppointments.getString("Title");
                String description = rsAppointments.getString("Description");
                String location = rsAppointments.getString("Location");
                String type = rsAppointments.getString("Type");

                Instant startInstant = rsAppointments.getTimestamp("Start").toInstant();
                ZonedDateTime startDateTime = ZonedDateTime.ofInstant(startInstant, ZoneId.of("UTC"));
                Instant endInstant = rsAppointments.getTimestamp("End").toInstant();
                ZonedDateTime endDateTime = ZonedDateTime.ofInstant(endInstant, ZoneId.of("UTC"));

                int customerId = rsAppointments.getInt("Customer_ID");
                int userId = rsAppointments.getInt("User_ID");
                int contactId = rsAppointments.getInt("Contact_ID");
                String contactName = Contacts.getContactName(contactId);

                appointments.add(new Appointment(id, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId, contactName));
            }
        }
        return appointments;
    }

    /**
     * This method sets the selected appointment
     */
    public static void setSelectedAppointment(Appointment selectedAppointment) {
        Appointments.selectedAppointment = selectedAppointment;
    }

    /**
     * This method gets the selected appointment
     */
    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

}
