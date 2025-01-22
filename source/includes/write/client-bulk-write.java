package org.example;

import com.mongodb.*;
import com.mongodb.client.model.*;
import com.mongodb.client.model.bulk.*;
import org.bson.Document;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import java.util.*;

public class QuickStart {
    public static void main(String[] args) {
        // Replace the placeholder with your Atlas connection string
        String uri = "<connection string>";

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
//            List<ClientNamespacedWriteModel> bulkOperations = new ArrayList<>();

            // start-insert-models
            MongoNamespace peopleNamespace = new MongoNamespace("db", "people");
            MongoNamespace thingsNamespace = new MongoNamespace("db", "things");

            ClientNamespacedInsertOneModel insertDocument1 = ClientNamespacedWriteModel
                    .insertOne(
                            peopleNamespace,
                            new Document("name", "Julia Smith")
            );

            ClientNamespacedInsertOneModel insertDocument2 = ClientNamespacedWriteModel
                    .insertOne(
                            thingsNamespace,
                            new Document("object", "washing machine")
            );
            // end-insert-models

            // start-replace-models
            MongoNamespace peopleNamespace = new MongoNamespace("db", "people");
            MongoNamespace thingsNamespace = new MongoNamespace("db", "things");

            ClientNamespacedReplaceOneModel replace_person = ClientNamespacedWriteModel
                    .replaceOne(
                            peopleNamespace,
                            Filters.eq("_id", 1),
                            new Document("name", "Frederic Hilbert")
                    );

            ClientNamespacedReplaceOneModel replace_thing = ClientNamespacedWriteModel
                    .replaceOne(
                            thingsNamespace,
                            Filters.eq("_id", 1),
                            new Document("object", "potato")
                    );
            // end-replace-models

            // start-perform
            MongoNamespace peopleNamespace = new MongoNamespace("db", "people");
            MongoNamespace thingsNamespace = new MongoNamespace("db", "things");

            List<ClientNamespacedWriteModel> bulkOperations = Arrays.asList(
                    ClientNamespacedWriteModel
                            .insertOne(
                                    peopleNamespace,
                                    new Document("name", "Corey Kopper")
                            ),
                    ClientNamespacedWriteModel
                            .replaceOne(
                                    thingsNamespace,
                                    Filters.eq("_id", 1),
                                    new Document("object", "potato")
                            )
            );
            Publisher<ClientBulkWriteResult> bulkWritePublisher = mongoClient.bulkWrite(
                    bulkOperations);

            ClientBulkWriteResult clientBulkResult = Mono.from(bulkWritePublisher).block();
            System.out.println(clientBulkResult.toString());
            // end-perform

            // start-options
            MongoNamespace namespace = new MongoNamespace("db", "people");

            ClientBulkWriteOptions options = ClientBulkWriteOptions
                    .clientBulkWriteOptions()
                    .ordered(false);

            List<ClientNamespacedWriteModel> bulkOperations = new ArrayList<>();

            bulkOperations.add(
                    ClientNamespacedWriteModel.insertOne(
                            namespace,
                            new Document("_id", 1).append("name", "Rudra Suraj")
                    )
            );

            // Causes a duplicate key error
            bulkOperations.add(
                    ClientNamespacedWriteModel.insertOne(
                            namespace,
                            new Document("_id", 1).append("name", "Mario Bianchi")
                    )
            );

            bulkOperations.add(
                    ClientNamespacedWriteModel.insertOne(
                            namespace,
                            new Document("name", "Wendy Zhang")
                    )
            );

            Publisher<ClientBulkWriteResult> bulkWritePublisher = mongoClient.bulkWrite(
                    bulkOperations, options);
            // end-options
        }
    }
}
