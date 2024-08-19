package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;

import com.mongodb.client.model.ClusteredIndexOptions;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.reactivestreams.client.*;
import org.bson.Document;
import reactor.core.publisher.Flux;

public class IndexOperations {
    public static void main(String[] args) {
        // Replace the placeholder with your Atlas connection string
        String uri = "<connection string URI>";

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
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            // start-single-field
            collection.createIndex(Indexes.ascending("<field name>"));
            // end-single-field

            // start-compound
            collection.createIndex(Indexes.ascending("<field name 1>", "<field name 2>"));
            // end-compound

            // start-multikey
            collection.createIndex(Indexes.ascending("<array field name>"));
            // end-multikey

            // start-search-create
            Document index = new Document("mappings", new Document("dynamic", true));
            collection.createSearchIndex("<index name>", index);
            // end-search-create

            // start-search-list
            ListSearchIndexesPublisher<Document> listIndexesPublisher = collection.listSearchIndexes();

            Flux.from(listIndexesPublisher)
                    .doOnNext(System.out::println)
                    .blockLast();
            // end-search-list

            // start-search-update
            Document newIndex = new Document("mappings", new Document("dynamic", true));
            collection.updateSearchIndex("<index name>", newIndex);
            // end-search-update

            // start-search-delete
            collection.dropIndex("<index name>");
            // end-search-delete

            // start-text
            collection.createIndex(Indexes.text("<field name>"));
            // end-text

            // start-geo
            collection.createIndex(Indexes.geo2dsphere("<GeoJSON object field>"));
            // end-geo

            // start-unique
            IndexOptions indexOptions = new IndexOptions().unique(true);
            collection.createIndex(Indexes.ascending("<field name>"), indexOptions);
            // end-unique

            // start-wildcard
            collection.createIndex(Indexes.ascending("$**"));
            // end-wildcard

            // start-clustered
            ClusteredIndexOptions clusteredIndexOptions = new ClusteredIndexOptions(
                    Indexes.ascending("_id"),
                    true
            );

            CreateCollectionOptions createCollectionOptions= new CreateCollectionOptions()
                    .clusteredIndexOptions(clusteredIndexOptions);

            MongoCollection<Document> collection = database.createCollection("<collection name",
                    createCollectionOptions);
            // end-clustered

            // start-remove
            collection.dropIndex("<index name>");
            // end-remove
        }
    }
}