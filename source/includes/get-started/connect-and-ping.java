package org.example;

import com.mongodb.*;
import helpers.SubscriberHelpers;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;




import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;



public class QuickStart {
    public static void main(String[] args) {
        // Replace the placeholder with your Atlas connection string
        String uri = "<connection string>";


        // Construct a ServerApi instance using the ServerApi.builder() method
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();


        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();


        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("admin");
            try {
                // Send a ping to confirm a successful connection
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                // Use subscriber methods to print result of ping
                SubscriberHelpers.ObservableSubscriber<Document> commandResult = new SubscriberHelpers.PrintSubscriber<>("Pinged your deployment. You successfully connected to MongoDB!");
                database.runCommand(command).subscribe(commandResult);
            } catch (MongoException me) {
                System.err.println(me);
            }
        }
    }
}
