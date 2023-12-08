package com.example.dbclientapp.helper;

import com.example.dbclientapp.dao.AppointmentsQuery;
import com.example.dbclientapp.model.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public abstract class TimeConflictChecker {

    /**
     * This method checks if a customer will have a time conflict
     */
    public static boolean checkConflict(ZonedDateTime utcStart, ZonedDateTime utcEnd, int customerId) throws SQLException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        ResultSet rsAppointments = AppointmentsQuery.selectAll();
        while (rsAppointments.next()) {
            if (rsAppointments.getInt("Customer_ID") == customerId) {
                Instant startInstant = rsAppointments.getTimestamp("Start").toInstant();
                ZonedDateTime startDateTime = ZonedDateTime.ofInstant(startInstant, ZoneId.of("UTC"));
                Instant endInstant = rsAppointments.getTimestamp("End").toInstant();
                ZonedDateTime endDateTime = ZonedDateTime.ofInstant(endInstant, ZoneId.of("UTC"));

                if (utcStart.equals(startDateTime)
                        || utcEnd.equals(endDateTime)
                        || (utcStart.isAfter(startDateTime) && utcStart.isBefore(endDateTime))
                        || (utcEnd.isAfter(startDateTime) && utcEnd.isBefore(endDateTime))) {
                    return false;
                }
            }
        }
        return true;
    }

}
