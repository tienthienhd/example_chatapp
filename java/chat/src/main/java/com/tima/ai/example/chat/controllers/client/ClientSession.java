package com.tima.ai.example.chat.controllers.client;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSession {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public ClientSession(Socket s){
        this.socket = s;
        try {
            this.input = new DataInputStream(s.getInputStream());
            this.output = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
