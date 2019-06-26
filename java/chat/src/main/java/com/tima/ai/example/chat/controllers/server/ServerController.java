package com.tima.ai.example.chat.controllers.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerController implements Runnable {

    private ServerSocket serverSocket;
    private boolean isListening;

    private Thread thread;

    public ServerController(){

    }

    public synchronized void startListening(int port){
        if (this.thread == null){
            try{
                this.serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.isListening = true;
            this.thread = new Thread(this);
            this.thread.start();
        }
        System.out.println("Server is started and listening on port: " + port);

    }

    public synchronized void stopListening(){
        this.isListening = false;
        if (this.serverSocket != null){
            try{
                this.serverSocket.close();
                this.serverSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(this.thread != null){
            this.thread.interrupt();
            this.thread = null;
        }

    }

    public void run() {
        try{
            while(this.isListening){
                Socket s = this.serverSocket.accept();
                System.out.println(s.getInetAddress().getHostAddress()
                        + ":" + s.getPort() + " has connected at "
                        + new Date().toString());
                ServerSession session = new ServerSession(s);
                session.start();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerController sc = new ServerController();
        sc.startListening(5000);
    }
}
