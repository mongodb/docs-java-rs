# start-limit-method
FindPublisher<Document> findPublisher = restaurants.find(
                eq("cuisine", "Italian")).limit(5);
List<Document> findResults = Flux.from(findPublisher)
        .collectList().block();

for (Document document : findResults) {
    System.out.println(document);
}
# end-limit-method

# start-sort-method
FindPublisher<Document> findPublisher = restaurants.find(
                eq("cuisine", "Italian")).sort(ascending("name"));
List<Document> findResults = Flux.from(findPublisher)
        .collectList().block();

for (Document document : findResults) {
    System.out.println(document.getString("name"));
}
# end-sort-method

# start-skip
FindPublisher<Document> findPublisher = restaurants.find(
                eq("borough", "Manhattan")).skip(10);
List<Document> findResults = Flux.from(findPublisher)
        .collectList().block();

for (Document document : findResults) {
    System.out.println(document.getString("name"));
}
# end-skip

# start-limit-sort-skip
FindPublisher<Document> findPublisher = restaurants.find(
                eq("cuisine", "Italian"))
        .sort(ascending("name"))
        .limit(5)
        .skip(10);
List<Document> findResults = Flux.from(findPublisher)
        .collectList().block();

for (Document document : findResults) {
    System.out.println(document.getString("name"));
}
# end-limit-sort-skip