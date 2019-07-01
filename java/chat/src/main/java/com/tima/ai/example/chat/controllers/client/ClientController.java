package com.tima.ai.example.chat.controllers.client;

import com.tima.ai.example.chat.models.Friend;
import com.tima.ai.example.chat.models.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientController implements IMsgListener, IClientListener{
    final static String responseOk = "ok";
    final static String responseNotOk = "not";

    private String username;

    private Socket socketServer;
    private DataInputStream inputServer;
    private DataOutputStream outputServer;

    private ClientSession clientSession;
    private ClientListener clientListener;

    private boolean isLogin = false;
    private String token = null;




    public ClientController(String serverIp, int port){
        boolean connectDone = this.connectServer(serverIp, port);
    }

    private boolean connectServer(String ip, int port){
        try {
            this.socketServer = new Socket(ip, port);
            this.inputServer = new DataInputStream(this.socketServer.getInputStream());
            this.outputServer = new DataOutputStream(this.socketServer.getOutputStream());
            return true;
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void listenClientConnect(){
        this.clientListener = new ClientListener(this);
        String address = this.clientListener.startListening(new int[] {9000, 9001, 9002, 9003, 9004, 9005, 9006, 9007, 9008, 9009, 9010, 9011, 9012});
        System.out.println("Listen on:" + address);
        this.assignAddress(this.username, address);
    }

    private String readLine() throws IOException {
        String line = this.inputServer.readUTF();
        System.out.println("<---" + line);
        return line;
    }

    private void sendLine(String line) throws Exception {
        if (socketServer == null){
            throw new Exception("Haven't connect server yet.");
        }
        System.out.println("--->" + line);
        this.outputServer.writeUTF(line);
        this.outputServer.flush();
    }

    public boolean login(String username, String password){
        try {
            String cmd = "|LOGIN|" + username + "|" + password;
            this.sendLine(cmd);
            String response = this.readLine();
            if (response.startsWith(responseNotOk)){
                throw new IOException("Login failed");
            } else {
                this.token = response.split("[|]")[1];
            }
            this.username = username;
            listenClientConnect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addFriend(String friendName){
        try {
            String cmd = this.token + "|ADD_FRIEND|" + this.username + "|" + friendName;
            this.sendLine(cmd);
            String response = this.readLine();
            if (response.equals(responseNotOk)){
                throw new IOException("Add friend failed");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendMsg(String sender, String receiver, String msg){
        try{
            this.sendLine(this.token + "|SEND_MSG|" + sender + "|" + receiver + "|" + msg);
            String response = readLine();
            if (response.startsWith(responseNotOk)){
                throw new Exception("Sent msg failed");
            }
            if (this.clientSession != null) {
                this.clientSession.sendMsg(msg);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean signUp(String usename, String password){
        try{
            this.sendLine("|SIGN_UP|" + usename + "|" + password);
            String response = this.readLine();
            if (response.equals(responseNotOk)){
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
            this.sendLine(this.token + "|LOGOUT|" + usename);
            String response = this.readLine();
            if (response.equals(responseNotOk)){
                throw new IOException("Sign up failed");
            }
            this.username = null;
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Friend> getListFriends(String username){
        try{
            this.sendLine(this.token + "|LIST_FRIEND|" + username);
            String response = this.readLine();
            if (response.startsWith(responseNotOk)){
                throw new IOException("Get list friend failed");
            }

            List<Friend> listFriends = new ArrayList<Friend>();
            response = response.substring(responseOk.length()+1);
            String[] res = response.split("[;]");
            for(String fs: res){
                String[] f = fs.split("[|]");
                String friendName = f[0];
                Date dateModified = new Date(f[1]);

                Friend friend = new Friend(username, friendName, dateModified);
                listFriends.add(friend);
            }
            return listFriends;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getListMsgs(String sender, String receiver){
        try{
            this.sendLine(this.token + "|LIST_MSG|" + sender + "|" + receiver);
            String response = readLine();
            if (response.startsWith(responseNotOk)){
                throw new IOException("Get list friend failed");
            }
            List<Message> listMsgs = new ArrayList<Message>();
            response = response.substring(responseOk.length() + 1);
            String[] res = response.split("[;]");
            for(String ms: res){
                String[] m = ms.split("[|]");
                String msgId = m[0];
                String sder = m[1];
                String recver = m[2];
                String msg = m[3];
                Date createdDate = new Date(m[4]);

                Message message = new Message(msgId, sder, recver, msg, createdDate);
                listMsgs.add(message);
            }
            return listMsgs;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void assignAddress(String username, String address){
        try {
            sendLine(this.token + "|ADD_ADDRESS|" + username + "|" + address);
            String response = readLine();
            if (response.startsWith(responseNotOk)){
                throw new Exception("update address failed");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getAddress(String username){
        try {
            sendLine(this.token + "|ADDRESS|" + username);
            String response = readLine();
            if (response.startsWith(responseNotOk)){
                throw new IOException("Get list friend failed");
            }
            System.out.println(response);
            return response.split("[|]")[1];

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean connectClient(String ip, int port){
        try {
            Socket s = new Socket(ip, port);
            this.clientSession = new ClientSession(s, this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean connectClient(String address){
        String[] addresss = address.split("[:]");
        String ip = addresss[0];
        int port = Integer.parseInt(addresss[1]);
        return connectClient(ip, port);
    }

    @Override
    public void receivedMsg(String msg) {
        System.out.println("Received msg: " + msg);
    }

    @Override
    public void hasConnect(Socket s) {
        this.clientSession = new ClientSession(s, this);
    }
}
