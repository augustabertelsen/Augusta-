//  bridge between WebServer and SearchEngine 
// handles search requests: it extracts the query from the HTTP request, calls the SearchEngine, 
// and translates the Page results into JSON for the WebServer.


package searchengine;

import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.List;

public class QueryHandler {
    private SearchEngine searchEngine;

    public QueryHandler(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    // main entry point called by WebServer
    //extracts the search term form HTTP request
    // passes the term to SearchEngine 
    // convert list og Pages to JSON 
    // return bytes 
    public byte[] handleSearch(HttpExchange io) {
        List<String> searchTerm = io.getRequestURI().getRawQuery().split("=");
        List<String> finalSearchTerms = new List<String>();
        List<Page> termPages = searchEngine(finalSearchTerms);

        if (searchterm.contains("OR")) {

        }
        else {}
    

        //code for SearchEngine class: 
        // if (page.containsAll(searchTerms) {
        // result.add(page)})
        //note -- SE class will have to be altered to accept a string

        //for (String word: searchTerm) {
          //  if (word.equals("OR")) {
            //    int 
            //}}

        
        List<String> jsonStrings = new ArrayList<>();

        // Each page must become a small JSON object before they can be combined
        // A JSON object string is just text that follows JSON rules.
        // Goal is for it to look like: {"url": "...", "title": "..."}
        for (Page page : termPages) {
            String url = page.getUrl();
            String title = page.getTitle();
            String json = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);

            //temporary storage before structure
            jsonStrings.add(json);
        }

        //Now we create one JSON array Stringthat is correctly structured
        String result = "[ ";
        for (int i = 0; i <= jsonStrings.size() - 1; i++) {
            result += jsonStrings.get(i);

            if (i < jsonStrings.size() - 1) {
                result += ", ";
            }
        }
        result += " ]";

        // convert String to byte
        // return byte
        result = result.getBytes();
        return result.getBytes();
    }


}
//NOTE ON JSON STRING LIST TO ONE JSON ARRAY String : 
// JSON string list creates:
// {"url":"A","title":"A"}
// {"url":"B","title":"B"}
// {"url":"C","title":"C"}
// JSON array string  creates:
// [{"url":"A","title":"A"}, {"url":"B","title":"B"}, {"url":"C","title":"C"}]
// NOT AN ARRAY!!! -> its a single string (JSON array)


// OldWebServer

// //receives HTTP search request
//   void search(HttpExchange io) {
//     var searchTerm = io.getRequestURI().getRawQuery().split("=")[1];

// //JSON formatting for response 
//       response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
//         page.get(0).substring(6), page.get(1)));
