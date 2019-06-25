package com.tima.ai.example.chat.controllers;

public class AuthenticateController extends Controller {

    public AuthenticateController() {
        super();
    }

    public int login(String username, String password){
        int userId = Controller.dbCon.checkLogin(username, password);
        return userId;
    }

    public boolean logout(){
        return true;
    }

    public boolean signUp(String username, String password){
        boolean done = Controller.dbCon.createUser(username, password);
        return done;
    }

}
