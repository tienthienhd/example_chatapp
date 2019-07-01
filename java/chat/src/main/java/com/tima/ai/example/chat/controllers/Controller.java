package com.tima.ai.example.chat.controllers;

import com.tima.ai.example.chat.models.MongoConnection;

import java.net.UnknownHostException;

public class Controller {
    protected static MongoConnection mongoCon;

    public Controller(){
        try {
            this.mongoCon = new MongoConnection();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
