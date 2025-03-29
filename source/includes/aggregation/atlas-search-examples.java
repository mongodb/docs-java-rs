package org.example;
import com.mongodb.*;
import com.mongodb.client.model.Projections;
import com.mongodb.reactivestreams.client.*;
import org.bson.Document;

import reactor.core.publisher.Mono;
import java.util.List;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.search.SearchOperator;
import static com.mongodb.client.model.search.SearchPath.fieldPath;
import org.reactivestreams.Publisher;

public class Main {
    public static void main(String[] args) {
        // Replace the placeholder with your Atlas connection string
        String uri = "mongodb+srv://admin:APap8822@cluster0.guj3b.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

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
            MongoCollection<Document> movies = database.getCollection("movies");

            // start atlasHelperMethods
            Bson searchStageFilters = Aggregates.search(
                    SearchOperator.compound()
                            .filter(
                                    List.of(
                                            SearchOperator.text(fieldPath("genres"), "Drama"),
                                            SearchOperator.phrase(fieldPath("cast"), "sylvester stallone"),
                                            SearchOperator.numberRange(fieldPath("year")).gtLt(1980, 1989),
                                            SearchOperator.wildcard(fieldPath("title"),"Rocky *")
                                    )));

            Bson projection = Aggregates.project(Projections.fields(
                    Projections.include("title", "year", "genres", "cast")
            ));

            List<Bson> aggregateStages = List.of(searchStageFilters,projection);

            Publisher<Document> publisher = movies.aggregate(aggregateStages);
            publisher.subscribe(new SubscriberHelpers.PrintDocumentSubscriber());
            Mono.from(publisher).block();
            // end atlasHelperMethods
        }
    }
}
