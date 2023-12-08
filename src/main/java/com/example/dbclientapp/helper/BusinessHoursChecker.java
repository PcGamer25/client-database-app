package com.example.dbclientapp.helper;

import java.time.ZonedDateTime;

public abstract class BusinessHoursChecker {

    /**
     * This method checks if a ZonedDateTime is within set business hours
     */
    public static boolean checkHours(ZonedDateTime utcZDT) {
        return (utcZDT.getHour() >= 12) || (utcZDT.getHour() <= 2);
    }

}
