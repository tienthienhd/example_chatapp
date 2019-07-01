package com.tima.ai.example.chat.models;

import com.mongodb.client.MongoCollection;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MongoConnectionTest {

    @Test
    void checkExistTrue() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        boolean done = mongoConnection.checkExist("tienthien");
        assertTrue(done);
    }

    @Test
    void checkExistFalse() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        boolean done = mongoConnection.checkExist("tienthien2");
        assertFalse(done);
    }

    @Test
    void loginTrue() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        boolean done = mongoConnection.login("tienthien", "pass");
        assertTrue(done);
    }

    @Test
    void loginFalse() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        boolean done = mongoConnection.login("tienthien2", "pass");
        assertFalse(done);
    }

    @Test
    void updateToken() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        boolean done = mongoConnection.updateToken("test2", "tienthien");
        assertTrue(done);
    }

    @Test
    void checkToken() {
    }

    @Test
    void createAccountTrue() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        boolean done = mongoConnection.createAccount("user2", "pass");
        assertTrue(done);
    }

    @Test
    void createAccountFalse() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        boolean done = mongoConnection.createAccount("user2", "pass");
        assertFalse(done);
    }

    @Test
    void logout() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        boolean done = mongoConnection.logout("tienthien");
        assertTrue(done);
    }

    @Test
    void addFriend() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        boolean done = mongoConnection.addFriend("tienthien", "user2");
        assertTrue(done);
    }

    @Test
    void getListFriends() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        List<Friend> listFriend = mongoConnection.getListFriends("tienthien");
        assertEquals(1, listFriend.size());
    }

    @Test
    void sendMessage() throws UnknownHostException {
        MongoConnection mongoConnection = new MongoConnection();
        boolean done = mongoConnection.sendMessage("tienthien", "user2", "hi");
        assertTrue(done);

    }

    @Test
    void getListMessages() {

    }
}