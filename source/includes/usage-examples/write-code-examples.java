# start-insert-one
Document document = new Document("<field name>", "<value>");
Mono<InsertOneResult> result = Mono.from(collection.insertOne(document));

result.doOnNext(i -> System.out.println(i.wasAcknowledged()))
      .doOnError(e -> e.printStackTrace())
      .doOnTerminate(mongoClient::close)
      .subscribe();
# end-insert-one

# start-insert-multiple
Document doc1 = new Document("<field name>", "<value>");
Document doc2 = new Document("<field name>", "<value>");

List<Document> documents = new ArrayList<Document>();

documents.add(doc1);
documents.add(doc2);

Mono<InsertManyResult> result = Mono.from(collection.insertMany(documents));

result.doOnNext(i -> System.out.println(i.wasAcknowledged()))
      .doOnError(e -> e.printStackTrace())
      .doOnTerminate(mongoClient::close)
      .subscribe();
# end-insert-multiple

# start-update-one
Mono<UpdateResult> result = Mono.from(collection.updateOne(eq("<field name>", "<value>"), set("<field name>", "<value>")));

result.doOnNext(i -> System.out.println(i.getModifiedCount()))
      .doOnError(e -> e.printStackTrace())
      .doOnTerminate(mongoClient::close)
      .subscribe();
# end-update-one

# start-update-multiple
Mono<UpdateResult> result = Mono.from(collection.updateMany(eq("<field name>", "<value>"), set("<field name>", "<value>")));

result.doOnNext(i -> System.out.println(i.getModifiedCount()))
      .doOnError(e -> e.printStackTrace())
      .doOnTerminate(mongoClient::close)
      .subscribe();
# end-update-multiple

# start-replace-one
Mono<UpdateResult> result = Mono.from(collection.replaceOne(eq("<field name>", "<value>"), new Document().append("<field name>", "<value>").append("<field name>", "<value>")));

result.doOnNext(i -> System.out.println(i.getModifiedCount()))
      .doOnError(e -> e.printStackTrace())
      .doOnTerminate(mongoClient::close)
      .subscribe();
# end-replace-one

# start-delete-one
Mono<DeleteResult> result = Mono.from(collection.deleteOne(eq("<field name>", "<value>")));

result.doOnNext(i -> System.out.println(i.getDeletedCount()))
      .doOnError(e -> e.printStackTrace())
      .doOnTerminate(mongoClient::close)
      .subscribe();
# end-delete-one

# start-delete-multiple
Mono<DeleteResult> result = Mono.from(collection.deleteMany(eq("<field name>", "<value>")));

result.doOnNext(i -> System.out.println(i.getDeletedCount()))
      .doOnError(e -> e.printStackTrace())
      .doOnTerminate(mongoClient::close)
      .subscribe();
# end-delete-multiple

# start-bulk-write
Mono<BulkWriteResult> result = Mono.from(collection.bulkWrite(
    Arrays.asList(new InsertOneModel<>(new Document("<field name>", "<value>")),
        new InsertOneModel<>(new Document("<field name>", "<value>")),
        new UpdateOneModel<>(new Document("<field name>", "<value>"),
                             new Document("$set", new Document("<field name>", "<value>"))),
        new DeleteOneModel<>(new Document("<field name>", "<value>")),
        new ReplaceOneModel<>(new Document("<field name>", "<value>"),
                              new Document("<field name>", "<value>").append("<field name>", "<value>")))));

result.doOnNext(i -> System.out.println(i))
      .doOnError(e -> e.printStackTrace())
      .doOnTerminate(mongoClient::close)
      .subscribe();
# end-bulk-write