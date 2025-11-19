// Handles HTTP /search requests. Extracts query, calls SearchEngine, and formats the JSON response.


package searchengine;

public class QueryHandler {
    
//receives HTTP search request
  void search(HttpExchange io) {
    var searchTerm = io.getRequestURI().getRawQuery().split("=")[1];

//JSON formatting for response 
      response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
        page.get(0).substring(6), page.get(1)));


}
