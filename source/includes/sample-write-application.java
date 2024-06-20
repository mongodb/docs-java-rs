import com.mongodb.*;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.reactivestreams.client.MongoCollection;
import helpers.SubscriberHelpers;
import helpers.SubscriberHelpers.ObservableSubscriber;
import org.bson.Document;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

public class updateDocument {
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
            MongoDatabase sample_restaurants = mongoClient.getDatabase("sample_restaurants");
            MongoCollection<Document> restaurants = sample_restaurants.getCollection("restaurants");

            try {
                // Start example code here

                // End example code here
            } catch (MongoException me) {
                System.err.println(me);
            }
        }
    }
}
