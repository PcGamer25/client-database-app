package com.example.dbclientapp.helper;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public abstract class ZDTToTimestamp {

    /**
     * This method converts a ZonedDateTime to a Timestamp
     */
    public static Timestamp toTimestamp(ZonedDateTime utcZDT) {
        return Timestamp.from(utcZDT.toInstant());
    }

}