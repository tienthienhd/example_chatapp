package com.tima.ai.example.chat.controllers.server;

import com.tima.ai.example.chat.controllers.AuthenticateController;
import com.tima.ai.example.chat.controllers.ChatController;
import com.tima.ai.example.chat.controllers.FriendController;
import com.tima.ai.example.chat.models.Friend;
import com.tima.ai.example.chat.models.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;

public class ServerSession implements Runnable {

    final static String responseOk = "ok";
    final static String responseNotOk = "not";


    private Socket socket;
    private Thread thread;
    private boolean isAlive;

    private DataInputStream input;
    private DataOutputStream output;

    private AuthenticateController authenticateController;
    private ChatController chatController;
    private FriendController friendController;

    private static HashMap<String, Integer> mapUsernamePort;

    public ServerSession(Socket s) throws IOException {
        this.socket = s;
        this.input = new DataInputStream(s.getInputStream());
        this.output = new DataOutputStream(s.getOutputStream());

        this.authenticateController = new AuthenticateController();
        this.chatController = new ChatController();
        this.friendController = new FriendController();

        this.mapUsernamePort = new HashMap<>();
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

    public void run() {
        String line = null;
        try {
            while (this.isAlive) {
                line = this.input.readUTF();
                if (line == null) {
                    break;
                }
                System.out.println("<---" + line);
                String[] listParams = line.split("[|]");

                String token = listParams[0];
                String cmd = listParams[1];
                String[] param = new String[listParams.length - 2];
                System.arraycopy(listParams, 2, param, 0, param.length);


                this.processCommand(token, cmd, param);
            }
        } catch (EOFException | SocketException e) {
            System.out.println("Connection is disconnect");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.stop();
        }
    }

    private void processCommand(String token, String cmd, String[] params) {
        cmd = cmd.toUpperCase();
        cmd = cmd.trim();

        if (cmd.equals("LOGIN")) {
            this.processLogin(params);
        } else if (cmd.equals("LOGOUT")) {
            if (checkAuthod(token)) {
                this.processLogout(params);
            }
        } else if (cmd.equals("SIGN_UP")) {
            this.processSignUp(params);
        } else if (cmd.equals("LIST_FRIEND")) {
            if (checkAuthod(token)) {
                this.processListFriends(params);
            }
        } else if (cmd.equals("ADD_FRIEND")) {
            if (checkAuthod(token)) {
                this.processAddFriend(params);
            }
        } else if (cmd.equals("LIST_MSG")) {
            if (checkAuthod(token)) {
                this.processListMsgs(params);
            }
        } else if (cmd.equals("SEND_MSG")) {
            if (checkAuthod(token)) {
                this.processSendMsg(params);
            }
        } else if (cmd.equals("ADD_ADDRESS")){
            if (checkAuthod(token)){
                this.processUpdateAddress(params);
            }
        } else if (cmd.equals("ADDRESS")) {
            if (checkAuthod(token)){
                this.processGetAddress(params);
            }
        }
    }

    private void processGetAddress(String[] params) {
        String username = params[0];
        String address = this.authenticateController.getAddress(username);
        if(address != null){
            sendResponse(responseOk + "|" + address);
        } else {
            sendResponse(responseNotOk);
        }
    }

    private void processUpdateAddress(String[] params) {
        String username = params[0];
        String address = params[1];

        if (this.authenticateController.updateAddress(username, address)){
            sendResponse(responseOk);
        } else {
            sendResponse(responseNotOk);
        }
    }

    private boolean checkAuthod(String token) {
        return this.authenticateController.checkAuth(token);
    }

    private void sendResponse(String res) {
        System.out.println("--->" + res);
        try {
            this.output.writeUTF(res);
            this.output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLogin(String[] params) {
//        String[] p = params.split("[|]");
        String username = params[0];
        String password = params[1];

        String token = this.authenticateController.login(username, password);
        if (token == null) {
            System.out.println("Login failed!");
            sendResponse(responseNotOk);
        } else {
            sendResponse(responseOk + "|" + token);
        }
    }

    private void processLogout(String[] params) {
        String username = params[0];
        this.authenticateController.logout(username);

    }

    private void processSignUp(String[] params) {
//        String[] p = params.split("[|]");
        String username = params[0];
        String password = params[1];

        boolean done = this.authenticateController.signUp(username, password);
        if (done) {
            this.processLogin(params);
            sendResponse(responseOk);
        } else {
            System.out.println("Sign up failed");
            sendResponse(responseNotOk);
        }

    }

    private void processListFriends(String[] params) {
        String username = params[0];
        List<Friend> listFriends = this.friendController.getListFriend(username);
        StringBuilder res = new StringBuilder();
        for (Friend f: listFriends
             ) {
            res.append(responseOk);
            res.append(";");
            res.append(f.getFriendName());
            res.append("|");
            res.append(f.getModifiedDate());
            res.append(";");
        }
        if(res.length() > 1){
            sendResponse(res.toString());
        } else {
            sendResponse(responseNotOk);
        }

    }

    private void processAddFriend(String[] params) {
        String username = params[0];
        String friendName = params[1];

        if (this.friendController.addFriend(username, friendName)){
            sendResponse(responseOk);
        } else {
            System.out.println("Add friend ok");
            sendResponse(responseNotOk);
        }

    }

    private void processListMsgs(String[] params) {
        String sender = params[0];
        String receiver = params[1];

        List<Message> listMsg = this.chatController.getListMsg(sender, receiver);
        StringBuilder res = new StringBuilder();
        for (Message m: listMsg){
            res.append(responseOk);
            res.append(";");
            res.append(m.getMsgId());
            res.append("|");
            res.append(m.getSender());
            res.append("|");
            res.append(m.getReceiver());
            res.append("|");
            res.append(m.getMsg());
            res.append("|");
            res.append(m.getCreateDate());
            res.append(";");
        }
        if(res.length() > 1){
            sendResponse(res.toString());
        } else {
            sendResponse(responseNotOk);
        }
    }

    private void processSendMsg(String[] params) {
        String sender = params[0];
        String receiver = params[1];
        String msg = params[2];

        if (this.chatController.sendMsg(sender, receiver, msg)){
            sendResponse("ok");
        } else {
            System.out.println("Sent msg failed");
            sendResponse(responseNotOk);
        }

    }
}
