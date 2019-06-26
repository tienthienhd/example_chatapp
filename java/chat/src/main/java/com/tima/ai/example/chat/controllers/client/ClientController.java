package com.tima.ai.example.chat.controllers.client;

import com.tima.ai.example.chat.models.Friend;
import com.tima.ai.example.chat.models.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.TreeMap;

public class ClientController {

    private Socket socketServer;
    private DataInputStream input;
    private DataOutputStream output;

    private ClientSession clientSession;

    private boolean isLogin = false;




    public ClientController(String serverIp, int port){
        boolean connectDone = this.connectServer(serverIp, port);
    }

    private boolean connectServer(String ip, int port){
        try {
            this.socketServer = new Socket(ip, port);
            this.input = new DataInputStream(this.socketServer.getInputStream());
            this.output = new DataOutputStream(this.socketServer.getOutputStream());
            return true;
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private String readLine() throws IOException {
        String line = this.input.readUTF();
        return line;
    }

    private void sendLine(String line) throws Exception {
        if (socketServer == null){
            throw new Exception("Haven't connect server yet.");
        }
        this.output.writeUTF(line + "\r\n");
    }

    public boolean login(String username, String password){
        try {
            String cmd = "LOGIN|" + username + "|" + password;
            this.sendLine(cmd);
            String response = this.readLine();
            if (response.equals("1")){
                throw new IOException("Login failed");
            }
            this.isLogin = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addFriend(int friendId){
        try {
            String cmd = "ADD_FRIEND|" + friendId;
            this.sendLine(cmd);
            String response = this.readLine();
            if (response.equals("1")){
                throw new IOException("Add friend failed");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendMsg(){
        return false;
    }

    public boolean signUp(String usename, String password){
        try{
            this.sendLine("SIGN_UP|" + usename + "|" + password);
            String response = this.readLine();
            if (response.equals("1")){
                throw new IOException("Sign up failed");
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean logout(String usename){
        try{
            this.sendLine("LOGOUT|" + usename);
            String response = this.readLine();
            if (response.equals("1")){
                throw new IOException("Sign up failed");
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Friend> getListFriends(){
        return null;
    }

    public List<Message> getListMsgs(){
        return null;
    }

    public static void main(String[] args) {
        ClientController cc = new ClientController("localhost", 5000);
        if (cc.login("tienthien", "pass")){
            System.out.println("login successful");
        }


    }

}
