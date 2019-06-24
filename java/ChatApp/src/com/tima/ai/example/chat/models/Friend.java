package com.tima.ai.example.chat.models;

import java.util.Date;

public class Friend {

    private int userId;
    private int friendId;
    private Date modifiedDate;

    public Friend() {
        this.userId = -1;
        this.friendId = -1;
        this.modifiedDate = new Date();
    }

    public Friend(int userId, int friendId, Date modifiedDate) {
        this.userId = userId;
        this.friendId = friendId;
        this.modifiedDate = modifiedDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
