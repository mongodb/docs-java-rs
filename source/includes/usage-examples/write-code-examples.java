# start-insert-one
Document document = new Document("<field name>", "<value>");
ObservableSubscriber<InsertOneResult> insertOneSubscriber = new SubscriberHelpers.OperationSubscriber<>();
collection.insertOne(document).subscribe(insertOneSubscriber);

ObservableSubscriber<InsertOneResult> result = insertOneSubscriber.await();

System.out.println(result.first().wasAcknowledged());
# end-insert-one

# start-insert-multiple
Document doc1 = new Document("<field name>", "<value>");
Document doc2 = new Document("<field name>", "<value>");

List<Document> documents = new ArrayList<Document>();

documents.add(doc1);
documents.add(doc2);

ObservableSubscriber<InsertManyResult> insertManySubscriber = new SubscriberHelpers.OperationSubscriber<>();
collection.insertMany(documents).subscribe(insertManySubscriber);

ObservableSubscriber<InsertManyResult> result = insertManySubscriber.await();

System.out.println(result.first().wasAcknowledged());
# end-insert-multiple

# start-update-one
ObservableSubscriber<UpdateResult> updateOneSubscriber = new SubscriberHelpers.OperationSubscriber<>();
collection.updateOne(eq("<field name>", "<value>"), set("<field name>", "<value>")).subscribe(updateOneSubscriber);

ObservableSubscriber<UpdateResult> result = updateOneSubscriber.await();

System.out.println(result.first().getModifiedCount());
# end-update-one

# start-update-multiple
ObservableSubscriber<UpdateResult> updateManySubscriber = new SubscriberHelpers.OperationSubscriber<>();
collection.updateMany(eq("<field name>", "<value>"), set("<field name>", "<value>")).subscribe(updateManySubscriber);

ObservableSubscriber<UpdateResult> result = updateManySubscriber.await();

System.out.println(result.first().getModifiedCount());
# end-update-multiple

# start-replace-one
ObservableSubscriber<UpdateResult> replaceOneSubscriber = new SubscriberHelpers.OperationSubscriber<>();
collection.replaceOne(eq("<field name>", "<value>"), new Document().append("<field name>", "<value>").append("<field name>", "<value>")).subscribe(replaceOneSubscriber);

ObservableSubscriber<UpdateResult> result = replaceOneSubscriber.await();

System.out.println(result.first().getModifiedCount());
# end-replace-one

# start-delete-one
ObservableSubscriber<DeleteResult> deleteOneSubscriber = new SubscriberHelpers.OperationSubscriber<>();
collection.deleteOne(eq("<field name>", "<value>")).subscribe(deleteOneSubscriber);

ObservableSubscriber<DeleteResult> result = deleteOneSubscriber.await();

System.out.println(result.first().getDeletedCount())
# end-delete-one

# start-delete-multiple
ObservableSubscriber<DeleteResult> deleteManySubscriber = new SubscriberHelpers.OperationSubscriber<>();
collection.deleteMany(eq("<field name>", "<value>")).subscribe(deleteManySubscriber);

ObservableSubscriber<DeleteResult> result = deleteManySubscriber.await();

System.out.println(result.first().getDeletedCount());
# end-delete-multiple

# start-bulk-write
ObservableSubscriber<BulkWriteResult> bulkWriteSubscriber = new SubscriberHelpers.OperationSubscriber<>();
collection.bulkWrite(
    Arrays.asList(new InsertOneModel<>(new Document("<field name>", "<value>")),
        new InsertOneModel<>(new Document("<field name>", "<value>")),
        new UpdateOneModel<>(new Document("<field name>", "<value>"),
                             new Document("$set", new Document("<field name>", "<value>"))),
        new DeleteOneModel<>(new Document("<field name>", "<value>")),
        new ReplaceOneModel<>(new Document("<field name>", "<value>"),
                              new Document("<field name>", "<value>").append("<field name>", "<value>"))))
.subscribe(bulkWriteSubscriber);

ObservableSubscriber<BulkWriteResult> result = bulkWriteSubscriber.await();

System.out.println(result);
# end-bulk-write