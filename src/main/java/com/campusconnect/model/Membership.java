package com.campusconnect.model;

public class Membership implements java.io.Serializable {
    private int id;
    private int clubId;
    private int userId;
    private String status;
    private String clubName;
    private String memberName;
    private String email;
    private java.sql.Timestamp joinedAt;

    public Membership() {}

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public java.sql.Timestamp getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(java.sql.Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }

}
