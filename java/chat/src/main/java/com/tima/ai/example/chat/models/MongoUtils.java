package com.tima.ai.example.chat.models;

import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.List;

public class MongoUtils {
    private static final String HOST = "localhost";
    private static final int PORT = 27017;

    public static MongoClient getMongoClient() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(HOST, PORT);
        return mongoClient;
    }

    private static void ping() throws UnknownHostException {
        MongoClient mongoClient = getMongoClient();

        System.out.println("List all DB:");

        // get list databases's name
        List<String> dbNames = mongoClient.getDatabaseNames();
        for (String dbName : dbNames) {
            System.out.println("+ DB Name: " + dbName);
        }

        System.out.println("Done!");
    }

    public static void main(String[] args) throws UnknownHostException {
        ping();
    }
}
