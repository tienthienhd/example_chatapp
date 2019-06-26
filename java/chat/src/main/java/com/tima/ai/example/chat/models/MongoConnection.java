package com.tima.ai.example.chat.models;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import javax.tools.DocumentationTool;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MongoConnection {

    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection userCollection;
    private MongoCollection friendCollection;
    private MongoCollection msgCollection;

    public MongoConnection() throws UnknownHostException {
        this.mongoClient = MongoUtils.getMongoClient();

        this.db = mongoClient.getDatabase("chat");
        this.userCollection = this.db.getCollection("user");
        this.friendCollection = this.db.getCollection("friend");
        this.msgCollection = this.db.getCollection("message");
    }


    public boolean checkExist(String username){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("username", username);
        FindIterable<Document> iterDoc = this.userCollection.find(whereQuery);
        return iterDoc.first() != null;
    }

    public boolean login(String username, String password){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("username", username);
        whereQuery.put("password", password);
        FindIterable<Document> iterDoc = this.userCollection.find(whereQuery);
        return iterDoc.first() != null;
    }

    public boolean updateToken(String token, String username){

        UpdateResult updateResult = this.userCollection.updateOne(Filters.eq("username", username), Updates.set("token", token));
        return updateResult.getModifiedCount() > 0;
    }

    public boolean checkToken(String token, String username){
        return false;

    }

    public boolean createAccount(String username, String password){
        if (this.checkExist(username)){
            return false;
        }
        Document document = new Document();
        document.append("username", username);
        document.append("password", password);
        document.append("token", "");
        this.userCollection.insertOne(document);
        return true;
    }

    public boolean logout(String username){
        return this.updateToken("", username);
    }

    public boolean addFriend(String username, String friendUsername){
        if (!this.checkExist(username) || !this.checkExist(friendUsername)){
            return false;
        }
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("username", username);
        whereQuery.put("friend_name", friendUsername);
        FindIterable<Document> iterDoc = this.friendCollection.find(whereQuery);
        if(iterDoc.first() != null) {
            return false;
        }

        Document document = new Document();
        document.append("username", username);
        document.append("friend_name", friendUsername);
        document.append("date", new Date());
        this.friendCollection.insertOne(document);
        return true;
    }

    public List<Friend> getListFriends(String username){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("username", username);
        FindIterable<Document> iterDoc = this.friendCollection.find(whereQuery);

        Iterator it = iterDoc.iterator();
        while(it.hasNext()){

        }
        return null;
    }

    public boolean sendMessage(String sender, String receiver, String msg){
        return false;
    }

    public List<Message> getListMessages(String sender, String receiver){
        // pagination
        return null;
    }
}
