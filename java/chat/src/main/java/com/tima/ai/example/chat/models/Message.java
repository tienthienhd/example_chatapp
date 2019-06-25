package com.tima.ai.example.chat.models;

import java.util.Date;

public class Message {


    private int msgId;
    private int senderId;
    private int receivedId;
    private String msgBody;
    private Date msgCreateDate;

    public Message() {
        this.msgId = -1;
        this.senderId = -1;
        this.receivedId = -1;
        this.msgBody = "";
        this.msgCreateDate = new Date();
    }

    public Message(int msgId, int senderId, int receivedId, String msgBody, Date msgCreateDate) {
        this.msgId = msgId;
        this.senderId = senderId;
        this.receivedId = receivedId;
        this.msgBody = msgBody;
        this.msgCreateDate = msgCreateDate;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceivedId() {
        return receivedId;
    }

    public void setReceivedId(int receivedId) {
        this.receivedId = receivedId;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public Date getMsgCreateDate() {
        return msgCreateDate;
    }

    public void setMsgCreateDate(Date msgCreateDate) {
        this.msgCreateDate = msgCreateDate;
    }
}
