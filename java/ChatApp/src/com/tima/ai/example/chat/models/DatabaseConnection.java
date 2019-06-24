package com.tima.ai.example.chat.models;


import java.sql.*;
import java.util.ArrayList;
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
        String url = "jdbc:mysql://localhost::3306/" + database;
        try{
            this.connection = DriverManager.getConnection(url, this.username, this.password);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<User> getUsers(){
        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery("Select * From users");

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
            PreparedStatement ps = this.connection.prepareStatement("Select * From friends where userId='?'");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkExist(String username){
        // TODO:
        return false;
    }

    public boolean checkLogin(String username, String password){
        // TODO:
        return false;
    }

    public List<Message> getMessages(int userId){
        // TODO:
        return null;
    }

    public boolean createUser(String username, String password){
        if (this.checkExist(username)){
            return false;
        } else {
            try {
                String cmd = "INSERT INTO users (username, password) VALUES ('?', '?')";
                PreparedStatement preparedStatement = this.connection.prepareStatement(cmd);
                preparedStatement.setString(1, username);
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

    public boolean addFriend(String username, String friendName){
        // TODO:
        return false;
    }
}
