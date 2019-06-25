package com.tima.ai.example.chat.controllers;

import com.tima.ai.example.chat.models.DatabaseConnection;

public class Controller {
    protected static DatabaseConnection dbCon;

    public Controller(){
        this.dbCon = new DatabaseConnection();
    }
}
