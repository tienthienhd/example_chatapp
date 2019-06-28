package com.tima.ai.example.chat.controllers.client;

import java.net.Socket;

public interface IClientListener {
    void hasConnect(Socket s);
}
