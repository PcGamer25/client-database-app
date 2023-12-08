package com.example.dbclientapp.model;

import com.example.dbclientapp.dao.CountriesQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Countries {

    /**
     * This method gets all country names from the database
     */
    public static ObservableList<String> getCountryNames() throws SQLException {
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        ResultSet rsCountries = CountriesQuery.selectAll();
        while (rsCountries.next()) {
            String country = rsCountries.getString("Country");
            countryNames.add(country);
        }
        return countryNames;
    }

    /**
     * This method gets the country name of a country ID
     */
    public static String getCountryName(int countryId) throws SQLException {
        ResultSet rsCountries = CountriesQuery.selectAll();
        while (rsCountries.next()) {
            if (rsCountries.getInt("Country_ID") == countryId) {
                return rsCountries.getString("Country");
            }
        }
        return null;
    }

    /**
     * This method gets the country ID of a country name
     */
    public static int getCountryId(String countryName) throws SQLException {
        ResultSet rsCountries = CountriesQuery.selectAll();
        while (rsCountries.next()) {
            if (rsCountries.getString("Country").equals(countryName)) {
                return rsCountries.getInt("Country_ID");
            }
        }
        return -1;
    }

}
