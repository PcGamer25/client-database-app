package com.example.dbclientapp.model;

import com.example.dbclientapp.dao.UsersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Users {

    private static int currentUserId = -1;

    /**
     * This method authenticates a username and password
     */
    public static boolean authenticate(String userName, String password) throws SQLException {
        ResultSet rsUsers = UsersQuery.selectAll();
        while (rsUsers.next()) {
            String rsUserName = rsUsers.getString("User_Name");
            String rsPassword = rsUsers.getString("Password");
            if (rsUserName.equals(userName) && rsPassword.equals(password)) {
                currentUserId = rsUsers.getInt("User_ID");
                return true;
            }
        }
        return false;
    }

    /**
     * This method gets all user IDs from the database
     */
    public static ObservableList<Integer> getUserIds() throws SQLException {
        ObservableList<Integer> userIds = FXCollections.observableArrayList();
        ResultSet rsUsers = UsersQuery.selectAll();
        while (rsUsers.next()) {
            int id = rsUsers.getInt("User_ID");
            userIds.add(id);
        }
        return userIds;
    }

    /**
     * This method gets the current user ID
     */
    public static int getCurrentUserId() {
        return currentUserId;
    }

}
