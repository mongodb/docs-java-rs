package org.query;

import com.mongodb.*;
import com.mongodb.reactivestreams.client.MongoCollection;
import helpers.SubscriberHelpers.ObservableSubscriber;
import helpers.SubscriberHelpers.PrintDocumentSubscriber;
import org.bson.Document;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

public class QueryDatabase {
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
        try (MongoClient mongoClient = MongoClients.create(settings))
        {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> movies = database.getCollection("movies");

           try {
                // Create a subscriber and query the database
             ObservableSubscriber<Document> documentSubscriber = new PrintDocumentSubscriber();
             movies.find(eq("title", "Back to the Future")).subscribe(documentSubscriber);
             documentSubscriber.await();

          } catch (MongoException me) {
               System.err.println(me);
          }
        }
    }
}
