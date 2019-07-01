package com.tima.ai.example.chat.controllers;

import com.tima.ai.example.chat.models.Message;

import java.util.List;

public class ChatController extends Controller {

    public ChatController(){
        super();
    }

    public boolean sendMsg(String sender, String receiver, String msg){
        return Controller.mongoCon.sendMessage(sender, receiver, msg);
    }

    public List<Message> getListMsg(String sender, String receiver){
        return Controller.mongoCon.getListMessages(sender, receiver, 10, null);
    }


}

