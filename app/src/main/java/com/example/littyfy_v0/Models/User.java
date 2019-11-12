package com.example.littyfy_v0.Models;

public class User {
    private String userName;
    private String userID;
    private boolean isOnline;

    public User(String userName, String userID, Boolean isOnline) {
        this.userName = userName;
        this.userID = userID;
        this.isOnline = isOnline;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
