package com.tima.ai.example.chat.controllers.server;


public class MainServer {
    public static void main(String[] args) {
        ServerController sc = new ServerController();
        sc.startListening(5000);
    }
}
