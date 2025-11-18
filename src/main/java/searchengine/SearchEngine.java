package searchengine;

import java.util.ArrayList;
import java.util.List;

public class SearchEngine {
  
    

 // handle search requests
  void search(HttpExchange io) {
    // extract query string from '?q=...'
    var searchTerm = io.getRequestURI().getRawQuery().split("=")[1];

    // list to accumulate JSON objects
    var response = new ArrayList<String>();

    // iterate through matching pages
    for (var page : search(searchTerm)) {
      // format each result as JSON fragment
      response.add(String.format(
        "{\"url\": \"%s\", \"title\": \"%s\"}",
        page.get(0).substring(6),  // page URL part after '*PAGE '
        page.get(1)                // title line
      ));
    }

    // encode JSON array into bytes
    var bytes = response.toString().getBytes(CHARSET);

    // send HTTP response
    respond(io, 200, "application/json", bytes);
  }





 // search pages for a term
  List<List<String>> search(String searchTerm) {
    // result list
    var result = new ArrayList<List<String>>();
    // linear scan through pages
    for (var page : pages) {
      // check if any line contains the term
      if (page.contains(searchTerm)) {
        result.add(page);
      }
    }
    // return matches
    return result;
  }

  


}
