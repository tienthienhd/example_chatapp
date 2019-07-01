package com.tima.ai.example.chat.models;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Projections.*;

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
        FindIterable<Document> iterDoc = this.userCollection.find(new Document("username", username));
        return iterDoc.first() != null;
    }

    public boolean login(String username, String password){
        Document whereQuery = new Document();
        whereQuery.append("username", username);
        whereQuery.append("password", password);
        FindIterable<Document> iterDoc = this.userCollection.find(whereQuery);
        return iterDoc.first() != null;
    }

    public boolean updateToken(String token, String username){

        UpdateResult updateResult = this.userCollection.updateOne(Filters.eq("username", username), Updates.set("token", token));
        return updateResult.getModifiedCount() > 0;
    }

    public String getToken(String username){
        Document q = new Document("username", username);
        FindIterable<Document> iterDoc = this.userCollection.find(q).projection(fields(include("token"), excludeId()));
        String token = iterDoc.first().getString("token");
        return token;
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
        Document whereQuery = new Document();
        whereQuery.append("username", username);
        whereQuery.append("friend_name", friendUsername);
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
        Document whereQuery = new Document();
        whereQuery.put("username", username);
        FindIterable<Document> iterDoc = this.friendCollection.find(whereQuery)
                .projection(fields(include("username", "friend_name", "date"), excludeId()));

        ArrayList<Friend> listFriends = new ArrayList<Friend>();
        Iterator it = iterDoc.iterator();
        while(it.hasNext()){
            Document d = (Document) it.next();
            String friend_name = d.getString("friend_name");
            Date date = d.getDate("date");
            Friend f = new Friend(username, friend_name, date);
            listFriends.add(f);
        }
        return listFriends;
    }

    public boolean sendMessage(String sender, String receiver, String msg){
        if (!this.checkExist(sender) || !this.checkExist(receiver)){
            return false;
        }

        Document d = new Document("sender", sender)
                .append("receiver", receiver)
                .append("msg", msg)
                .append("created_date", new Date());
        this.msgCollection.insertOne(d);
        return true;
    }

    public List<Message> getListMessages(String sender, String receiver, int pageSize, String lastId){
        Document query = null;
        // pagination
        if(lastId != null) {
            query = new Document("_id", new Document("$lt", new ObjectId(lastId)))
                    .append("sender", sender)
                    .append("receiver", receiver);
        } else {
            query = new Document()
                    .append("sender", sender)
                    .append("receiver", receiver);
        }
        FindIterable<Document> iterMsg = this.msgCollection.find(query).sort(new Document("_id", -1)).limit(pageSize);

        ArrayList<Message> listMsgs = new ArrayList<Message>();
        Iterator it = iterMsg.iterator();
        while(it.hasNext()){
            Document d = (Document) it.next();

            String msgId = d.getObjectId("_id").toString();
            String msg = d.getString("msg");
            Date created_date = d.getDate("created_date");

            Message m = new Message(msgId, sender, receiver, msg, created_date);
            listMsgs.add(m);
        }

        return listMsgs;
    }

    public String getAddress(String username){
        Document q = new Document("username", username);
        FindIterable<Document> iterDoc = this.userCollection.find(q).projection(fields(include("address"), excludeId()));
        String address = iterDoc.first().getString("address");
        return address;
    }

    public boolean updateAddress(String username, String address) {
        UpdateResult updateResult = this.userCollection.updateOne(Filters.eq("username", username), Updates.set("address", address));
        return true;
    }

//    public static void main(String[] args) throws UnknownHostException {
//        MongoConnection mongoConnection = new MongoConnection();
//        List<Message> listMsgs = mongoConnection.getListMessages("tienthien", "user2", 1, null);
//        String lastId = listMsgs.get(listMsgs.size() - 1).getMsgId();
//        for (Message m: listMsgs
//             ) {
//            System.out.println(m);
//        }
//        System.out.println("page 1: " + lastId);
//        listMsgs = mongoConnection.getListMessages("tienthien", "user2", 1, lastId);
//        for (Message m: listMsgs
//             ) {
//            System.out.println(m);
//        }
//
//    }
}
