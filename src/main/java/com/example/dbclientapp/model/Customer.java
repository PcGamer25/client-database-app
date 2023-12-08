package com.example.dbclientapp.model;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private int division;

    /**
     * Constructor for a customer
     */
    public Customer(int id, String name, String address, String postal, String phone, int division) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.division = division;
    }

    /**
     * Gets the customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the customer ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the customer postal code
     */
    public String getPostal() {
        return postal;
    }

    /**
     * Sets the customer postal code
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     * Gets the customer phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the customer phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the customer division ID
     */
    public int getDivision() {
        return division;
    }

    /**
     * Gets the customer division ID
     */
    public void setDivision(int division) {
        this.division = division;
    }

}
