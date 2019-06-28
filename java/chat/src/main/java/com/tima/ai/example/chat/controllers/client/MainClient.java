package com.tima.ai.example.chat.controllers.client;


public class MainClient {
    public static void main(String[] args) {
        ClientController cc = new ClientController("localhost", 5000);
        if (cc.login("tienthien", "pass")){
            System.out.println("login successful");
        }
        int port = cc.getPort("user2");
        cc.connectClient("localhost", port);
        cc.sendMsg("tienthien", "user2", "test sent client");
//        cc.addFriend("user3");

//        cc.sendMsg("user2", "tienthien", "test send method");

    }
}
