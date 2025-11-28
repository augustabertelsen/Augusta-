package searchengine;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import searchengine.Page;

public class SearchEngine {

// CHANGED: Instead of an InvertedIndex, we now store all pages directly.
    private List<Page> pages;
    private List<Page> result;

    private InvertedIndex index;
    private List<List<String>> termList;

    public SearchEngine(InvertedIndex index) {

        this.index = index;
    }

    public byte[] handleSearch(HttpExchange io) {

        List<String> searchTerm = io.getRequestURI().getRawQuery().split("=");
        QueryHandler handler = new QueryHandler();
        termList = handler.handleQuery(searchTerm);

        List<Page> termPages = new ArrayList<>();

        termPages = search(termList);

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

    public List<Page> search(List<List<String>> termlist){
        // Create result list to collect matching pages.
        result = new ArrayList<Page>();
        
       
        // loop through the list of list of string aka termlist
        for (List list : termlist){
            // Create a list of list of pages - 
            List<List<Page>>pagesWithEachWord = new ArrayList<List<Page>>();
            // if the list in the list is not empty
            if(!list.isEmpty()){
                // loop through the words in that list
                for(String word: list){
                    //list to store pages whit that word 
                    List<Page> pagesWithWord = new ArrayList();
                    //look op word in index and add the pages containing that word to the list of pages pages whit word 
                    pagesWithWord = index.lookUp(word);

                    if (!pagesWithWord.isEmpty()) {
                    pagesWithEachWord.add(pagesWithWord);}

                    else {
                        pagesWithEachWord.clear();
                        break;
                    }
                    // if the list pageswhit word is not empty, add that list to the list of list of pages
                    
                }
                if (!pagesWithEachWord.isEmpty()){
                listResult = pagesWithEachWord(0);

                for (i=1; i<pagesWithEachWord.length()-1; i++) {
                    listResult = listResult.retainAll(pagesWithEachWord(i));
                }

                result.add(listResult);
            }

            else {
                //Task 2 - throw error message 
            
            }}
        }
        

        // ADDED: Loop through every page we know.
        for (Page page : pages) {
            // ADDED: Get the words from the page and check if they contain the cleaned term.
            if (page.getWords().contains(cleanSearchTerm)) {
                // ADDED: If yes, add page to the result list.
                result.add(page);
            }
        }

        return result;
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


