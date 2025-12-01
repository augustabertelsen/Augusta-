package searchengine;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import searchengine.Page;

public class SearchEngine {

    private InvertedIndex index;

    public SearchEngine(InvertedIndex index) {

        this.index = index;
    }

    public byte[] handleSearch(HttpExchange io) {
        String rawQueery = io.getRequestURI().getRawQuery().split("=")[1];
        String[] searchWordArray = rawQueery.split(" ");
        List<String> searchTerm = new ArrayList<String>(Arrays.asList(searchWordArray));
        QueryHandler handler = new QueryHandler();
        List<List<String>> termList = handler.handleQuery(searchTerm);

        List<Page> termP = search(termList);
        byte[] finalResult = finalResultBuilder(termP);
        return finalResult;
    }


    public byte[] finalResultBuilder(List<Page>termPages){ 

        List<String> jsonStrings = new ArrayList<String>();

    // Loop through termPages and build small JSON objects from each Page.
    // OBS!!! change name, and unmethod
    for (Page page : termPages) {
            String url = page.getUrl();
            String title = page.getTitle();
            String json = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);

            //temporary storage before structure
            jsonStrings.add(json);
        }

    // CHANGED: Kept name 'result', but now build it using a StringBuilder first.
    StringBuilder resultBuilder = new StringBuilder();

    // CHANGED: Start JSON array with '['.
    resultBuilder.append("[");

    // CHANGED: Copy old behavior: join JSON strings with ", ".
    for(
    int i = 0;i<jsonStrings.size();i++)
    {
        resultBuilder.append(jsonStrings.get(i));
        if (i < jsonStrings.size() - 1) {
            resultBuilder.append(", ");
        }
    }

    // CHANGED: Close JSON array with ']'.
    resultBuilder.append("]");

    // CHANGED: Turn builder into final String named 'result' (same name as old
    // code).
    String result = resultBuilder.toString();

    // CHANGED: Old code overwrote result with bytes (wrong type).
    // New: convert String result to byte[] once and return it.
    return result.getBytes(StandardCharsets.UTF_8);
}

    public List<Page> search(List<List<String>> termList) {
        // Create result list to collect matching pages.
        List<Page> result = new ArrayList<Page>();

        // loop through the list of list of string aka termlist
        if (!termList.isEmpty()) {

            for (int i = 0; i < termList.size(); i++) {
                List<String> currentList = termList.get(i);
                List<List<Page>> listWhitListOfPagesForEachWord = new ArrayList<List<Page>>();

                if (!currentList.isEmpty()) {
                    List<Page> pagesWithWord = new ArrayList<Page>();

                    for (int ii = 0; ii < currentList.size(); ii++) {
                        String currentWord = currentList.get(ii);
                        pagesWithWord = index.lookUp(currentWord);

                        if (!pagesWithWord.isEmpty()) {
                            listWhitListOfPagesForEachWord.add(pagesWithWord);
                        }

                        else {
                            listWhitListOfPagesForEachWord.clear();
                            break;
                        }
                    }
                }

                if (!listWhitListOfPagesForEachWord.isEmpty()) {

                    List<Page> listResult = new ArrayList<Page>();
                    // FOR CHARLIE: added get, also in the next part.
                    listResult = listWhitListOfPagesForEachWord.get(0);

                    for (int iii = 1; iii < listWhitListOfPagesForEachWord.size(); iii++) {
                        List<Page> currentListOfPages = new ArrayList<Page>();
                        currentListOfPages = listWhitListOfPagesForEachWord.get(iii);

                        listResult.retainAll(currentListOfPages);
                    }

                    if (!listResult.isEmpty()) {

                        for (int iiii = 0; iiii < listResult.size(); iiii++) {
                            Page currentPage = listResult.get(iiii);
                            result.add(currentPage);
                        }
                    }
                }
            }
           

        }
    
         return result;
    }
}