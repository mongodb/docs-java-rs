# start-project-include
FindPublisher<Document> findProjectionPublisher = restaurants.find(eq("name", "Emerald Pub"))
        .projection(fields(include("name", "cuisine", "borough")));
List<Document> findResults = Flux.from(findProjectionPublisher)
        .collectList().block();

for (Document document : findResults) {
    System.out.println(document);
}
# end-project-include

# start-project-include-without-id
FindPublisher<Document> findProjectionPublisher = restaurants.find(eq("name", "Emerald Pub"))
        .projection(fields(include("name", "cuisine", "borough"), excludeId()));
List<Document> findResults = Flux.from(findProjectionPublisher)
        .collectList().block();

for (Document document : findResults) {
    System.out.println(document);
}
# end-project-include-without-id

# start-project-exclude
FindPublisher<Document> findProjectionPublisher = restaurants.find(eq("name", "Emerald Pub"))
        .projection(fields(exclude("grades", "address")));
List<Document> findResults = Flux.from(findProjectionPublisher)
        .collectList().block();

for (Document document : findResults) {
    System.out.println(document);
}
# end-project-exclude