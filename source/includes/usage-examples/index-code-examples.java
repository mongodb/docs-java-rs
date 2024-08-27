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
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IndexExamples {
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
            Publisher<String> result = collection.createIndex(Indexes.ascending("<field name>"));
            Mono.from(result).block();
            // end-single-field

            // start-compound
            Publisher<String> result = collection.createIndex(Indexes.ascending("<field name 1>", "<field name 2>"));
            Mono.from(result).block();
            // end-compound

            // start-multikey
            Publisher<String> result = collection.createIndex(Indexes.ascending("<array field name>"));
            Mono.from(result).block();
            // end-multikey

            // start-search-create
            Document index = new Document("mappings", new Document("dynamic", true));
            Publisher<String> result = collection.createSearchIndex("<index name>", index);
            Mono.from(result).block();
            // end-search-create

            // start-search-list
            ListSearchIndexesPublisher<Document> listIndexesPublisher = collection.listSearchIndexes();

            Flux.from(listIndexesPublisher)
                    .doOnNext(System.out::println)
                    .blockLast();
            // end-search-list

            // start-search-update
            Document newIndex = new Document("mappings", new Document("dynamic", true));
            Publisher<Void> result = collection.updateSearchIndex("<index name>", newIndex);
            Mono.from(result).block();
            // end-search-update

            // start-search-delete
            Publisher<Void> result = collection.dropIndex("<index name>");
            Mono.from(result).block();
            // end-search-delete

            // start-text
            Publisher<String> result = collection.createIndex(Indexes.text("<field name>"));
            Mono.from(result).block();
            // end-text

            // start-geo
            Publisher<String> result = collection.createIndex(Indexes.geo2dsphere("<GeoJSON object field>"));
            Mono.from(result).block();
            // end-geo

            // start-unique
            IndexOptions indexOptions = new IndexOptions().unique(true);
            Publisher<String> result = collection.createIndex(Indexes.ascending("<field name>"), indexOptions);
            Mono.from(result).block();
            // end-unique

            // start-wildcard
            Publisher<String> result = collection.createIndex(Indexes.ascending("$**"));
            Mono.from(result).block();
            // end-wildcard

            // start-clustered
            ClusteredIndexOptions clusteredIndexOptions = new ClusteredIndexOptions(
                    Indexes.ascending("_id"),
                    true
            );

            CreateCollectionOptions createCollectionOptions= new CreateCollectionOptions()
                    .clusteredIndexOptions(clusteredIndexOptions);

            Publisher<Void> clusteredCollection = database.createCollection("<collection name>",
                    createCollectionOptions);
            Mono.from(clusteredCollection).block();
            // end-clustered

            // start-remove
            Publisher<Void> result = collection.dropIndex("<index name>");
            Mono.from(result).block();
            // end-remove
        }
    }
}