//package com.tima.ai.example.chat.models;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DatabaseConnectionTest {
//
//    @Test
//    void getUsers() {
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        List<User> listUser = databaseConnection.getUsers();
//        assertEquals(listUser.size(), 1);
//
//    }
//
//    @Test
//    void getFriends() {
//        int userId = 1;
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        List<Friend> listFriends = databaseConnection.getFriends(userId);
//        assertEquals(listFriends.size(), 0);
//    }
//
//    @Test
//    void checkExist() {
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        boolean exist = databaseConnection.checkExist("not exist");
//        assertFalse(exist);
//    }
//
//    @Test
//    void checkExistTrue(){
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        boolean exist = databaseConnection.checkExist("tienthien");
//        assertTrue(exist);
//    }
//
//    @Test
//    void checkLogin() {
//        String username = "tienthiendfa";
//        String password = "pass";
//
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        int done =databaseConnection.checkLogin(username, password);
//        assertEquals(done, 0);
//    }
//
//    @Test
//    void getMessages() {
//        int senderId = 1;
//        int receiverId = 1;
//
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        List<Message> listMsgs = databaseConnection.getMessages(senderId, receiverId);
//        assertEquals(listMsgs.size(), 0);
//    }
//
//    @Test
//    void createUser() {
//        String username = "user2";
//        String password = "pass";
//
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        boolean done = databaseConnection.createUser(username, password);
//        assertTrue(done);
//
//
//    }
//
//    @Test
//    void addFriend() {
//        int userId = 1;
//        int friendId = 2;
//
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        boolean done = databaseConnection.addFriend(userId, friendId);
//        assertTrue(done);
//    }
//
//    @Test
//    void addMessage() {
//        int senderId = 1;
//        int receiverId = 2;
//        String msg = "First message. Hello!";
//
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        boolean done = databaseConnection.addMessage(senderId, receiverId, msg);
//        assertTrue(done);
//    }
//}