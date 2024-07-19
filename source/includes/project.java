import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;


import org.bson.Document;
import org.bson.conversions.Bson;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Projections.*;

class QueryDatabase {
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
           MongoDatabase database = mongoClient.getDatabase("sample_restaurants");
           MongoCollection<Document> restaurants = database.getCollection("restaurants");

            //  start-project-include
            FindPublisher<Document> findProjectionPublisher = restaurants.find(
                            eq("name", "Emerald Pub"))
                    .projection(fields(include("name", "cuisine", "borough")));
            List<Document> findResults = Flux.from(findProjectionPublisher)
                    .collectList().block();

            for (Document document : findResults) {
                System.out.println(document);
            }
            //  end-project-include

            //  start-project-include-without-id
            FindPublisher<Document> findProjectionPublisher = restaurants.find(
                            eq("name", "Emerald Pub"))
                    .projection(fields(include("name", "cuisine", "borough"), excludeId()));
            List<Document> findResults = Flux.from(findProjectionPublisher)
                    .collectList().block();

            for (Document document : findResults) {
                System.out.println(document);
            }
            //  end-project-include-without-id

            //  start-project-exclude
            FindPublisher<Document> findProjectionPublisher = restaurants.find(
                            eq("name", "Emerald Pub"))
                    .projection(fields(exclude("grades", "address")));
            List<Document> findResults = Flux.from(findProjectionPublisher)
                    .collectList().block();

            for (Document document : findResults) {
                System.out.println(document);
            }
            //  end-project-exclude

        }
    }
}