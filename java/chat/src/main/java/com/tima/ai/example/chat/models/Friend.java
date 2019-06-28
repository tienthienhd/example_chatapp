package com.tima.ai.example.chat.models;

import java.util.Date;

public class Friend {

    private String username;
    private String friendName;
    private Date modifiedDate;

    public Friend() {
        this.username = "";
        this.friendName = "";
        this.modifiedDate = new Date();
    }

    public Friend(String username, String friendName, Date modifiedDate) {
        this.username = username;
        this.friendName = friendName;
        this.modifiedDate = modifiedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
