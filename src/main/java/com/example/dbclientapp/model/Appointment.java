package com.example.dbclientapp.model;

import java.time.ZonedDateTime;

public class Appointment {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    /**
     * Constructor for an appointment
     */
    public Appointment(int id, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerId, int userId, int contactId, String contactName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;

    }

    /**
     * Gets the appointment ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the appointment ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the appointment type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the appointment start date and time
     */
    public ZonedDateTime getStart() {
        return start;
    }

    /**
     * Sets the appointment start date and time
     */
    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    /**
     * Gets the appointment end date and time
     */
    public ZonedDateTime getEnd() {
        return end;
    }

    /**
     * Sets the appointment end date and time
     */
    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    /**
     * Gets the appointment customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the appointment customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the appointment user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the appointment user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the appointment contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the appointment contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets the appointment contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the appointment contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
