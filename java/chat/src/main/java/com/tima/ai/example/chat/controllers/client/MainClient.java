package com.tima.ai.example.chat.controllers.client;


public class MainClient {
    public static void main(String[] args) {
        ClientController cc = new ClientController("localhost", 5000);
        if (cc.login("user2", "pass")){
            System.out.println("login successful");
        }
        cc.addFriend("tienthien");


    }
}
