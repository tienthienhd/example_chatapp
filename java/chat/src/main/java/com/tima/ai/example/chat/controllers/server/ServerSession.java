package com.tima.ai.example.chat.controllers.server;

import com.tima.ai.example.chat.controllers.AuthenticateController;
import com.tima.ai.example.chat.controllers.ChatController;
import com.tima.ai.example.chat.controllers.FriendController;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class ServerSession implements Runnable {

    private Socket socket;
    private Thread thread;
    private boolean isAlive;

    private DataInputStream input;
    private DataOutputStream output;

    private int userId;
    private AuthenticateController authenticateController;
    private ChatController chatController;
    private FriendController friendController;

    public ServerSession(Socket s) throws IOException {
        this.socket = s;
        this.input = new DataInputStream(s.getInputStream());
        this.output = new DataOutputStream(s.getOutputStream());

        this.authenticateController = new AuthenticateController();
        this.chatController = new ChatController();
        this.friendController = new FriendController();
    }

    public  void start(){
        this.isAlive = true;
        if (this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void stop(){
        try {
            if (this.socket != null){
                this.socket.close();
                this.socket = null;
            }

            if (this.thread != null){
                this.thread.interrupt();
                this.thread = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        String line = null;
        try{
            while (this.isAlive){
                line = this.input.readUTF();
                if (line == null){
                    break;
                }
                System.out.println(line);
                String cmd = null;
                String param = null;
                int i = line.indexOf("|");
                if (i != -1) {
                    cmd = line.substring(0, i);
                    param = line.substring(i).trim();
                    param = param.substring(1);
                } else {
                    cmd = line;
                }

                this.processCommand(cmd, param);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.stop();
        }
    }

    private void processCommand(String cmd, String params){
        cmd = cmd.toUpperCase();
        cmd = cmd.trim();

        if (cmd.equals("LOGIN")){
            this.processLogin(params);
        } else if (cmd.equals("LOGOUT")){
            this.processLogout(params);
        } else if (cmd.equals("SIGN_UP")){
            this.processSignUp(params);
        } else if (cmd.equals("LIST_FRIEND")){
            this.processListFriends(params);
        } else if (cmd.equals("ADD_FRIEND")){
            this.processAddFriend(params);
        } else if (cmd.equals("LIST_MSG")){
            this.processListMsgs(params);
        } else if (cmd.equals("SEND_MSG")){
            this.processSendMsg(params);
        }
    }

    private void sendResponse(String res) {
        res = res + "\r\n";

        try {
            this.output.writeUTF(res);
            this.output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLogin(String params) {
        String[] p = params.split("[|]");
        String username = p[0];
        String password = p[1];

        String token = this.authenticateController.login(username, password);
        if(userId < 1){
            System.out.println("Login failed!");
            sendResponse("1");
        } else {
            this.userId = userId;
            sendResponse("0");
        }


    }

    private void processLogout(String params){
        String username = params;
//        this.authenticateController.checkAuth();

    }

    private void processSignUp(String params){
        String[] p = params.split("[|]");
        String username = p[0];
        String password = p[1];

        boolean done = this.authenticateController.signUp(username, password);
        if (done){
            this.processLogin(params);
            sendResponse("0");
        } else {
            System.out.println("Sign up failed");
            sendResponse("1");
        }

    }

    private void processListFriends(String params){

    }

    private void processAddFriend(String params){

    }

    private void processListMsgs(String params){

    }

    private void processSendMsg(String params){

    }
}
