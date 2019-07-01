package com.tima.ai.example.chat.controllers.client;

public class MainClient2 {
    public static void main(String[] args) {
        String username = "user3";
        String pass = "pass";
        ClientController cc = new ClientController("localhost", 5000);
        if (cc.login(username, pass)){
            System.out.println("login successful");
        }
        String friend = "tienthien";
        String address = cc.getAddress(friend);
        System.out.println("address of friend:" + address);
        if(cc.connectClient(address)){
            cc.sendMsg(username, friend, "send from user3 to tienthien");
        }

//        int port = cc.getPort("user2");
//        if(cc.connectClient("localhost", port)) {
//            cc.sendMsg("user3", "user2", "msg from user3 to user2");
//        }
//        cc.addFriend("user3");

//        cc.sendMsg("user2", "tienthien", "test send method");

    }
}
