package com.tima.ai.example.chat.models;


import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class DatabaseConnection {


    private Connection connection = null;
    private String username = null;
    private String password = null;
    private String database = null;

    public DatabaseConnection(){
        this.username = "root";
        this.password = "";
        this.database = "chatapp";
        this.createConnection();
    }

    public DatabaseConnection(String username, String password, String database){
        this.username = username;
        this.password = password;
        this.database = database;

        this.createConnection();
    }

    private void createConnection(){
        String url = "jdbc:mysql://localhost:3306/" + database;
        try{
            this.connection = DriverManager.getConnection(url, this.username, this.password);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<User> getUsers(){
        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");

            ArrayList<User> listUser = new ArrayList<User>();
            while (rs.next()){
                User u = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
                listUser.add(u);
            }
            return listUser;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Friend> getFriends(int userId){
        try{
            PreparedStatement ps = this.connection.prepareStatement("Select * From friends where user_id=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            ArrayList<Friend> listFriends = new ArrayList<Friend>();
            while(rs.next()){
                Friend f = new Friend(rs.getInt("user_id"), rs.getInt("friend_id"), rs.getDate("date_modified"));
                listFriends.add(f);
            }
            return listFriends;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkExist(String username) {
        try {
            String q = "SELECT COUNT(username) FROM users WHERE username='" + username + "'";
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(q);
            if (rs.next()) {
                boolean notExist = rs.getInt(1) == 0;
                return !notExist;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public int checkLogin(String username, String password){
        try{
            String q = "SELECT user_id, COUNT(username) FROM users WHERE username='" + username + "' AND password='" + password + "'";
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(q);
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public List<Message> getMessages(int senderId, int receiverId){
        try{
            PreparedStatement ps = this.connection.prepareStatement("Select * From messages where sender_id=? AND receiver_id=?");
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ResultSet rs = ps.executeQuery();

            ArrayList<Message> listMsgs = new ArrayList<Message>();
            while(rs.next()){
                Message msg = new Message(rs.getInt("msgId"), rs.getInt("sender_id"), rs.getInt("receiver_id"),
                        rs.getString("msg_body"), rs.getDate("msg_creation_date"));

                listMsgs.add(msg);
            }
            return listMsgs;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean createUser(String username, String password){
        if (this.checkExist(username)){
            return false;
        } else {
            try {
                String cmd = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement preparedStatement = this.connection.prepareStatement(cmd);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                int row = preparedStatement.executeUpdate();

                if(row == 1){
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean addFriend(int userId, int friendId){

        String cmd = "INSERT INTO friends (user_id, friend_id, date_modified) VALUES (?,?,?)";

        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(cmd);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, friendId);
            preparedStatement.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            int row = preparedStatement.executeUpdate();
            return row == 1;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean addMessage(int senderId, int receiverId, String msg){
        String cmd = "INSERT INTO messages(sender_id, receiver_id, msg_body,msg_creation_date) VALUES (?,?,?,?)";
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(cmd);
            preparedStatement.setInt(1, senderId);
            preparedStatement.setInt(2, receiverId);
            preparedStatement.setString(3, msg);
            preparedStatement.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            int row = preparedStatement.executeUpdate();
            return row == 1;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
