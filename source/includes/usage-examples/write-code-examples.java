# start-insert-one
Document document = new Document("<field name>", "<value>");
Mono<InsertOneResult> result = Mono.from(collection.insertOne(document));

System.out.printf("Inserted 1 document with ID %s%n.", result.block().getInsertedId());
# end-insert-one

# start-insert-multiple
Document doc1 = new Document("<field name>", "<value>");
Document doc2 = new Document("<field name>", "<value>");

List<Document> documents = Arrays.asList(doc1, doc2);

Mono<InsertManyResult> result = Mono.from(collection.insertMany(documents));

System.out.printf("Inserted documents with IDs %s%n.", result.block().getInsertedIds());
# end-insert-multiple

# start-update-one
Mono<UpdateResult> result = Mono.from(collection.updateOne(
      eq("<field name>", "<value>"),
      set("<field name>", "<new value>")));

System.out.printf("Inserted %s document with ID %s%n.", result.block().getModifiedCount(), result.block().getUpsertedId());
# end-update-one

# start-update-multiple
Mono<UpdateResult> result = Mono.from(collection.updateMany(
      eq("<field name>", "<value>"),
      set("<field name>", "<new value>")));

System.out.printf("Inserted %s documents with ID %s%n.", result.block().getModifiedCount(), result.block().getUpsertedId());
# end-update-multiple

# start-replace-one
Mono<UpdateResult> result = Mono.from(collection.replaceOne(
      eq("<field name>", "<value>"),
      new Document().append("<field name>", "<new value>")
      .append("<new field name>", "<new value>")));

System.out.printf("Replaced %s document with ID %s%n.", result.block().getModifiedCount(), result.block().getUpsertedId());
# end-replace-one

# start-delete-one
Mono<DeleteResult> result = Mono.from(collection.deleteOne(eq("<field name>", "<value>")));

System.out.printf("Deleted %s document.", result.block().getDeletedCount());
# end-delete-one

# start-delete-multiple
Mono<DeleteResult> result = Mono.from(collection.deleteMany(eq("<field name>", "<value>")));

System.out.printf("Deleted %s documents.", result.block().getDeletedCount());
# end-delete-multiple

# start-bulk-write
Mono<BulkWriteResult> result = Mono.from(collection.bulkWrite(
    Arrays.asList(new InsertOneModel<>(new Document("<field name>", "<value>")),
        new InsertOneModel<>(new Document("<field name>", "<value>")),
        new UpdateOneModel<>(new Document("<field name>", "<value>"),
                             new Document("$set", new Document("<field name>", "<new value>"))),
        new DeleteOneModel<>(new Document("<field name>", "<value>")),
        new ReplaceOneModel<>(new Document("<field name>", "<value>"),
                              new Document("<field name>", "<new value>").append("<new field name>", "<new value>")))));

System.out.printf("Modified %s documents and deleted %s%n documents.", result.block().getModifiedCount(), result.block().getDeletedCount);
# end-bulk-write