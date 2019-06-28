package com.tima.ai.example.chat.models;

import java.util.Date;

public class Message {

    private String msgId;
    private String sender;
    private String receiver;
    private String msg;
    private Date createDate;

    public Message() {
        this.sender = "";
        this.receiver = "";
        this.msg = "";
        this.createDate = new Date();
    }

    public Message(String msgId, String sender, String receiver, String msg, Date createDate) {
        this.msgId = msgId;
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
        this.createDate = createDate;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString(){
        return "msgId: " + this.msgId + "\nSender: " + this.sender + "\nReceiver: " + this.receiver + "\n Msg: " + this.msg;
    }
}
