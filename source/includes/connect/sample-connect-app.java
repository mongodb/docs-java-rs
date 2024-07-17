import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;

import org.bson.BsonDocument;
import org.bson.BsonInt64;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.conversions.Bson;
import reactor.core.publisher.Mono;

class ConnectionApp {
    public static void main(String[] args) {
        //start example code here
        
        //end example code here
        {
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Mono.from(mongoClient.runCommand(command))
                    .doOnSuccess(x -> System.out.println("Pinged your deployment. You successfully connected to MongoDB!"))
                    .doOnError(err -> System.out.println("Error: " + err.getMessage()))
                    .block();

            //other application code

        }
    }
}