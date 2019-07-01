package com.tima.ai.example.chat.controllers.client;

import com.tima.ai.example.chat.controllers.server.ServerSession;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ClientListener implements Runnable{

    private ServerSocket serverSocket;
    private boolean isListening;
    private Thread thread;

    private IClientListener clientController;

    public ClientListener(IClientListener clientController){
        this.clientController = clientController;
    }

    public synchronized String startListening(int[] ports){
        String address = null;
        if (this.thread == null){
            for (int port: ports) {
                try {
                    this.serverSocket = new ServerSocket(port);
                    address = this.serverSocket.getInetAddress().getHostAddress() + ":" + this.serverSocket.getLocalPort();
                    break;
                } catch (IOException e) {
                    continue;
                }
            }

            this.isListening = true;
            this.thread = new Thread(this);
//            this.thread.setDaemon(true);
            this.thread.start();
            return address;
        }
        return address;
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
}
