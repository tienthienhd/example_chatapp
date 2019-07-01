package com.tima.ai.example.chat.controllers;

import com.tima.ai.example.chat.models.Friend;

import java.util.List;

public class FriendController extends Controller {
    public FriendController(){
        super();
    }

    public boolean addFriend(String username, String friendName){
        boolean done = Controller.mongoCon.addFriend(username, friendName);
        return done;
    }

    public List<Friend> getListFriend(String username){
        return Controller.mongoCon.getListFriends(username);
    }
}
