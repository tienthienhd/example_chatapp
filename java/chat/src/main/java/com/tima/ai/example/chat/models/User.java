package com.tima.ai.example.chat.models;

public class User {

    private int userId;
    private String username;
    private String password;

    public User(){
        this.userId = -1;
        this.username = "";
        this.password = "";
    }

    public User(int userId, String username, String password){
        this.userId = userId;
        this.username = username;
        this.password = password;

    }

    public int getUserId(){
        return this.userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
