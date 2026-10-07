package com.campusconnect.model;

public class Certificate implements java.io.Serializable {
    private int id;
    private int userId;
    private int eventId;
    private String title;
    private java.sql.Date issuedOn;
    private String studentName;

    public Certificate() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public java.sql.Date getIssuedOn() {
        return issuedOn;
    }

    public void setIssuedOn(java.sql.Date issuedOn) {
        this.issuedOn = issuedOn;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}
