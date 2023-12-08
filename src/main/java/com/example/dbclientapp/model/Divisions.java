package com.example.dbclientapp.model;

import com.example.dbclientapp.dao.DivisionsQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Divisions {

    /**
     * This method gets the country name of a division ID
     */
    public static String getCountry(int divisionId) throws SQLException {
        ResultSet rsDivisions = DivisionsQuery.selectAll();
        while (rsDivisions.next()) {
            if (rsDivisions.getInt("Division_ID") == divisionId) {
                int countryId = rsDivisions.getInt("Country_ID");
                return Countries.getCountryName(countryId);
            }
        }
        return null;
    }

    /**
     * This method gets the division ID of a division name
     */
    public static int getDivisionId(String divisionName) throws SQLException {
        ResultSet rsDivisions = DivisionsQuery.selectAll();
        while (rsDivisions.next()) {
            if (rsDivisions.getString("Division").equals(divisionName)) {
                return rsDivisions.getInt("Division_ID");
            }
        }
        return -1;
    }

    /**
     * This method gets the division name of a division ID
     */
    public static String getDivisionName(int divisionId) throws SQLException {
        ResultSet rsDivisions = DivisionsQuery.selectAll();
        while (rsDivisions.next()) {
            if (rsDivisions.getInt("Division_ID") == divisionId) {
                return rsDivisions.getString("Division");
            }
        }
        return null;
    }

}
