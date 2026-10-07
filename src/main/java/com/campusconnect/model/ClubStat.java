package com.campusconnect.model;

public class ClubStat implements java.io.Serializable {
    private String name;
    private int members;
    private int events;
    private int regs;

    public ClubStat() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getEvents() {
        return events;
    }

    public void setEvents(int events) {
        this.events = events;
    }

    public int getRegs() {
        return regs;
    }

    public void setRegs(int regs) {
        this.regs = regs;
    }

}
