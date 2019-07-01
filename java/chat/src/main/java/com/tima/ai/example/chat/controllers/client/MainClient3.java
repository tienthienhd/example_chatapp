package com.tima.ai.example.chat.controllers.client;

public class MainClient3 {
    public static void main(String[] args) {
        ClientController cc = new ClientController("localhost", 5000);
        if (cc.login("user2", "pass")){
            System.out.println("login successful");
        }
//        int port = cc.getPort("tienthien");
//        cc.connectClient("localhost", port);
//        cc.sendMsg("user2", "tienthien", "test sent msg user to tienthien");
//        cc.addFriend("user3");

//        cc.sendMsg("user2", "tienthien", "test send method");

    }
}
