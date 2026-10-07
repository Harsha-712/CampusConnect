package com.campusconnect.model;

public class EventRegistration implements java.io.Serializable {
    private int eventId;
    private int userId;
    private boolean attended;

    public EventRegistration() {}

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

}
