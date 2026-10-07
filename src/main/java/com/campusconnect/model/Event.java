package com.campusconnect.model;

public class Event implements java.io.Serializable {
    private int id;
    private int clubId;
    private String title;
    private String description;
    private java.sql.Date eventDate;
    private java.sql.Time eventTime;
    private String location;
    private java.sql.Date regDeadline;
    private int capacity;
    private String status;
    private String code;
    private String club;
    private int taken;
    private int mine;
    private String state;

    public Event() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(java.sql.Date eventDate) {
        this.eventDate = eventDate;
    }

    public java.sql.Time getEventTime() {
        return eventTime;
    }

    public void setEventTime(java.sql.Time eventTime) {
        this.eventTime = eventTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public java.sql.Date getRegDeadline() {
        return regDeadline;
    }

    public void setRegDeadline(java.sql.Date regDeadline) {
        this.regDeadline = regDeadline;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getTaken() {
        return taken;
    }

    public void setTaken(int taken) {
        this.taken = taken;
    }

    public int getMine() {
        return mine;
    }

    public void setMine(int mine) {
        this.mine = mine;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
