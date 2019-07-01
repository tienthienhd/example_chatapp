package com.tima.ai.example.chat.controllers.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ClientSession implements Runnable{
    private Thread thread;
    private boolean isAlive;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private IMsgListener msgListener;

    public ClientSession(Socket s, IMsgListener msgListener){
        this.socket = s;
        this.msgListener = msgListener;
        try {
            this.input = new DataInputStream(s.getInputStream());
            this.output = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        this.isAlive = true;
        if (this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void stop() {
        try {
            if (this.socket != null) {
                this.socket.close();
                this.socket = null;
            }

            if (this.thread != null) {
                this.thread.interrupt();
                this.thread = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void run() {
        String line = null;
        try {
            while (this.isAlive) {
                line = this.input.readUTF();
                if (line == null) {
                    break;
                }
                System.out.println(line);
                this.msgListener.receivedMsg(line);

            }
        } catch (EOFException e) {
            System.out.println("Connection is disconnect");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.stop();
        }
    }

    public void sendMsg(String line) throws Exception {
        if (socket == null){
            throw new Exception("Haven't connect server yet.");
        }
        this.output.writeUTF(line);
        this.output.flush();
    }
}
