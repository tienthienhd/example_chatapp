package com.tima.ai.example.chat.controllers;

public class FriendController extends Controller {
    public FriendController(){
        super();
    }

    public boolean addFriend(int userId, int friendId){
        boolean done = Controller.dbCon.addFriend(userId, friendId);
        return done;
    }
}
