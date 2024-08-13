// start-specify-connection-string

import java.util.Arrays;

ConnectionString connectionString = new ConnectionString(
        "mongodb+srv://<db_username>:<db_password>@<cluster-url>/?compressors=snappy,zlib,zstd");

MongoClient mongoClient = MongoClients.create(connectionString);
// end-specify-connection-string

// start-specify-uri
MongoClientSettings settings = MongoClientSettings.builder()
        .compressorList(Arrays.asList(MongoCompressor.createSnappyCompressor(),
                MongoCompressor.createZlibCompressor(),
                MongoCompressor.createZstdCompressor()))
        .build();

MongoClient client = MongoClients.create(settings);
// end-specify-uri