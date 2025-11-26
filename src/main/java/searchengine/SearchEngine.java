//  import java.util.ArrayList;
// import java.util.List;

// import searchengine.InvertedIndex;
// import searchengine.Page;
    
// public class SearchEngine {

//    private InvertedIndex index;
//    private List<List<String>> termList;

// public SearchEngine(InvertedIndex index) {
//     this.index = index;
// }

// public byte[] handleSearch(HttpExchange io) {
//         List<String> searchTerm = io.getRequestURI().getRawQuery().split("=");
//         handler = new QueryHandler(searchTerm);

//          List<Page> termPages = new List<Pages>();
//         //takes the List<List<String>> provided by QueryHandler and searches for pages which contain all of the Strings in each List<String> and returns them as a List<Page>

        
//        //search index for pages containing all terms in term list and return as termPages

//          List<String> jsonStrings = new ArrayList<>();

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

// }}