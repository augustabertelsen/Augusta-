/**
 * OldQueryHandler takes an incoming HTTP search request and turns it into a JSON response.
 * OldQueryHandler is not a search because it never decides which pages match the term; 
 * it only reads the HTTP request, forwards the term to OldSearchEngine, and formats the results as JSON.
 *
 * INPUT:
 * - Receives an HttpExchange for a URL like ".../search?q=dogs".
 * - Reads the raw query string (for example "q=dogs").
 * - Extracts the value after "q=" (the search term, for example "dogs").
 * - Sends this search term to OldSearchEngine by calling searchEngine.search(searchTerm).
 *
 * PROCESS:
 * - Gets back a List<Page> from OldSearchEngine containing all matching pages.
 * - For each Page, builds a small JSON object with its URL and title.
 * - Joins all these JSON objects into one JSON array string.
 *
 * OUTPUT:
 * - Converts the final JSON string into a byte[] (UTF‑8).
 * - Returns that byte[] so WebServer can send it as the HTTP response body.
 *
 * CHANGES FROM ORIGINAL VERSION:
 * - No longer mixes broken OR-logic and JSON building in the same place.
 * - Uses correct types when reading the query string (String, not List<String>).
 * - OldQueryHandler now only handles HTTP details and JSON formatting;
 *   OldSearchEngine is responsible for deciding which pages match the search term.
 */

package searchengine;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;


public class OldQueryHandler {

    private final OldSearchEngine searchEngine;


    public OldQueryHandler(OldSearchEngine searchEngine) {

        this.searchEngine = searchEngine;
    }

    
    public byte[] handleSearch(HttpExchange io) {
        // CHANGED: Get the raw query string from the URL (e.g. "q=dogs").
        String rawQuery = io.getRequestURI().getRawQuery();

        // ADDED: If there is no query at all, return an empty JSON array.
        if (rawQuery == null || rawQuery.isEmpty()) {
            String emptyJson = "[]";
            return emptyJson.getBytes(StandardCharsets.UTF_8);
        }

        // CHANGED: now it's a String not a List.
        // Split rawQuery into key and value, and store only the value as searchTerm.
        String[] parts = rawQuery.split("=", 2);
        String searchTerm = parts.length > 1 ? parts[1] : "";

        // ADDED: Decode URL encoding so "big+dog" becomes "big dog".
        searchTerm = java.net.URLDecoder.decode(searchTerm, StandardCharsets.UTF_8);

        // CHANGED: Keep name 'termPages' but fix its type and initialization. Old: List<Page> termPages = new List<Pages>();
        List<Page> termPages = new ArrayList<>();

        // CHANGED: For now, ignore "OR" logic and just do a simple single-term search.
        // We keep the idea that SearchEngine does the actual searching.
        // Old idea: termPages = searchEngine.search(searchTerm);
        termPages = searchEngine.search(searchTerm);

        List<String> jsonStrings = new ArrayList<>();

        // Loop through termPages and build small JSON objects from each Page.
        for (Page page : termPages) {
            //  Use Page getters instead of raw lists.
            String url = page.getUrl();
            String title = page.getTitle();
            String json = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);
            jsonStrings.add(json);
        }

        // CHANGED: Kept name 'result', but now build it using a StringBuilder first.
        StringBuilder resultBuilder = new StringBuilder();

        // CHANGED: Start JSON array with '['.
        resultBuilder.append("[");

        // CHANGED: Copy old behavior: join JSON strings with ", ".
        for (int i = 0; i < jsonStrings.size(); i++) {
            resultBuilder.append(jsonStrings.get(i));
            if (i < jsonStrings.size() - 1) {
                resultBuilder.append(", ");
            }
        }

        // CHANGED: Close JSON array with ']'.
        resultBuilder.append("]");

        // CHANGED: Turn builder into final String named 'result' (same name as old code).
        String result = resultBuilder.toString();

        // CHANGED: Old code overwrote result with bytes (wrong type).
        // New: convert String result to byte[] once and return it.
        return result.getBytes(StandardCharsets.UTF_8);
    }



       // REMOVED: All the old broken code with:
    //  - List<String> searchTerm = io.getRequestURI().getRawQuery().split("=");
    //  - List<Page> termPages = new List<Pages>();
    //  - if (searchTerm.contains("OR")) { ... }
    // Because:
    //  - getRawQuery().split("=") returns a String[], so assigning it to List<String> and then calling
    //    contains(...) on it does not work.
    //  - new List<Pages>() cannot be instantiated (List is an interface, and 'Pages' is not a valid type).
    //  - the unfinished OR-logic mixed together with JSON-building made the method too complex and still non-working.
    //
    // If you later want OR / AND logic, add it cleanly by:
    //  - Parsing the query string into one or more search term Strings,
    //  - Calling searchEngine.search(...) for each term (or term group),
    //  - Merging / combining the resulting Page lists as needed,
    //  - Then running the same JSON-building code on the final list of Pages.
}






// Old query handler:


// public byte[] handleSearch(HttpExchange io) {
//         List<String> searchTerm = io.getRequestURI().getRawQuery().split("=");
//         //List<String> finalSearchTerms = new List<String>();
//         List<Page> termPages = new List<Pages>();

//         if (searchTerm.contains("OR")) {
//             for (String word: searchTerm) {
//                 if (!word.equals("OR")) { 
//                     if (!termPages.contains(searchEngine(word))){
//                     termPages.add(searchEngine.search(word));}
//         }

//         else{
//             termPages = searchEngine.search(searchTerm);
//                 }
    

//         //code for SearchEngine class: 
//         // if (page.containsAll(searchTerm) {
//         // result.add(page)})
//         //note -- SE class will have to be altered to accept a string

//         //for (String word: searchTerm) {
//           //  if (word.equals("OR")) {
//             //    int 
//             //}}

        
//         List<String> jsonStrings = new ArrayList<>();

//         // Each page must become a small JSON object before they can be combined
//         // A JSON object string is just text that follows JSON rules.
//         // Goal is for it to look like: {"url": "...", "title": "..."}
//         for (Page page : termPages) {
//             String url = page.getUrl();
//             String title = page.getTitle();
//             String json = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);

//             //temporary storage before structure
//             jsonStrings.add(json);
//         }

//         //Now we create one JSON array Stringthat is correctly structured
//         String result = "[ ";
//         for (int i = 0; i <= jsonStrings.size() - 1; i++) {
//             result += jsonStrings.get(i);

//             if (i < jsonStrings.size() - 1) {
//                 result += ", ";
//             }
//         }
//         result += " ]";

//         // convert String to byte
//         // return byte
//         result = result.getBytes();
//         return result.getBytes();

//         // handleSearch() should return a UTF-8 encoded byte[] containing the full JSON response.
//         // What needs to be done:
//             // Keep the final JSON as a String; do not overwrite it with bytes
//             // At the very end, convert the JSON string to byte[] once using UTF-8
//             // Return that byte[], not the String

//     }public byte[] handleSearch(HttpExchange io) {
//         List<String> searchTerm = io.getRequestURI().getRawQuery().split("=");
//         //List<String> finalSearchTerms = new List<String>();
//         List<Page> termPages = new List<Pages>();

//         if (searchTerm.contains("OR")) {
//             for (String word: searchTerm) {
//                 if (!word.equals("OR")) { 
//                     if (!termPages.contains(searchEngine(word))){
//                     termPages.add(searchEngine.search(word));}
//         }

//         else{
//             termPages = searchEngine.search(searchTerm);
//                 }
    

//         //code for SearchEngine class: 
//         // if (page.containsAll(searchTerm) {
//         // result.add(page)})
//         //note -- SE class will have to be altered to accept a string

//         //for (String word: searchTerm) {
//           //  if (word.equals("OR")) {
//             //    int 
//             //}}

        
//         List<String> jsonStrings = new ArrayList<>();

//         // Each page must become a small JSON object before they can be combined
//         // A JSON object string is just text that follows JSON rules.
//         // Goal is for it to look like: {"url": "...", "title": "..."}
//         for (Page page : termPages) {
//             String url = page.getUrl();
//             String title = page.getTitle();
//             String json = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);

//             //temporary storage before structure
//             jsonStrings.add(json);
//         }

//         //Now we create one JSON array Stringthat is correctly structured
//         String result = "[ ";
//         for (int i = 0; i <= jsonStrings.size() - 1; i++) {
//             result += jsonStrings.get(i);

//             if (i < jsonStrings.size() - 1) {
//                 result += ", ";
//             }
//         }
//         result += " ]";

//         // convert String to byte
//         // return byte
//         result = result.getBytes();
//         return result.getBytes();

//         // handleSearch() should return a UTF-8 encoded byte[] containing the full JSON response.
//         // What needs to be done:
//             // Keep the final JSON as a String; do not overwrite it with bytes
//             // At the very end, convert the JSON string to byte[] once using UTF-8
//             // Return that byte[], not the String

//     }





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
