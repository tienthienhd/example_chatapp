package com.tima.ai.example.chat.controllers;

public class ChatController extends Controller {

    public ChatController(){
        super();
    }

    public boolean sendMsg(int senderId, int receiverId, String msg){
        boolean done = Controller.dbCon.addMessage(senderId, receiverId, msg);
        return done;
    }


}

