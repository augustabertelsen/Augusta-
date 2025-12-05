package searchengine;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for accepting a query from the server, splitting it 
 * into a list of strings and calling the QueryHandler class on that list. SearchEngine
 * then takes the resulting List<List<String>> list of search terms and uses the InvertedIndex to compile
 * a list of pages which match each search term. It then loops through these pages and converts each one 
 * to a JSON compliant format, which is returned.  
 */
 

public class SearchEngine {

    private final InvertedIndex index;
    private final Ranking rank;

/**
 * Creates a new SearchEngine with an InvertedIndex object which organises a set of files. 
 * @param index an InvertedIndex object which the class will use to find pages that match the search terms. 
 */
    public SearchEngine(InvertedIndex index, Ranking rank) {

        this.index = index;
        this.rank = rank;
    }

    /**
     * Extracts the search terms from the raw query and adds them to an array list, which the 
     * handleQuery() method of QueryHandler can then be called upon. The List<List<String>> 
     * returned by this method then has the search() method called upon it, and the resulting 
     * List<Page> has the finalResultBuilder() method called upon it, which produces an array of bytes.  
     * @param rawQuery String request from the HttpExchange from the Server.  
     * @return an array of bytes.
     */
    public byte[] handleSearch(String rawQuery) {
        String decodedQuery = URLDecoder.decode(rawQuery, StandardCharsets.UTF_8);
        String[] searchWordArray = decodedQuery.split(" ");
        List<String> nonEmptySearchWords = new ArrayList<>();
        for (String searchTerm : searchWordArray){
            if (!searchTerm.isEmpty()){
                nonEmptySearchWords.add(searchTerm);
            }
        }
        QueryHandler handler = new QueryHandler();
        List<List<String>> termList = handler.handleQuery(nonEmptySearchWords);

        List<Page> resultPages = search(termList);
        List<Page> resultPagesSortedByRank = rank.getPageListSortedByranking(resultPages, termList);
        return finalResultBuilder(resultPagesSortedByRank);
    }

    /**
     * For every page in the List<Page> compiled by the search() method, this method builds a small JSON object. 
     * It then creates an array of these JSON strings and converts that array to a String. 
     * Finally, this String is converted to an array of bytes. 
     * @param termPages the list of Pages compiled by the search() method. 
     * @return an array of bytes.
     */

    public byte[] finalResultBuilder(List<Page>termPages){ 

        List<String> jsonStrings = new ArrayList<>();

    for (Page page : termPages) {
            String url = page.getUrl();
            String title = page.getTitle();
            String json = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);

            jsonStrings.add(json);
        }

    StringBuilder resultBuilder = new StringBuilder();
    resultBuilder.append("[");

    for(
    int i = 0;i<jsonStrings.size();i++)
    {
        resultBuilder.append(jsonStrings.get(i));
        if (i < jsonStrings.size() - 1) {
            resultBuilder.append(", ");
        }
    }

    resultBuilder.append("]");

    String result = resultBuilder.toString();

    return result.getBytes(StandardCharsets.UTF_8);
}
    /**
     * This method receives as a parameter a list of search terms. For each word in one of these search terms, 
     * the method creates a list of pages which include that word using the lookUp() method of the InvertedIndex
     * object, and adds all of these lists of pages to a list. It then takes the first list of pages in this list
     * and then loops through the rest, using the retailAll() java method to keep only the pages which 'match' 
     * every word in teh search term. This list of pages is returned. 
     * @param termList the list of list of Strings, or list of search terms, produced by the QueryHandler.
     * @return a list of pages which correspond to one of these search terms. 
     */
    public List<Page> search(List<List<String>> termList) {
        
        List<Page> result = new ArrayList<>();

        if (!termList.isEmpty()) {

            for (int i = 0; i < termList.size(); i++) {
                List<String> currentList = termList.get(i);
                List<List<Page>> listWithListOfPagesForEachWord = new ArrayList<>();

                if (!currentList.isEmpty()) {
                    List<Page> pagesWithWord = new ArrayList<Page>();

                    for (int ii = 0; ii < currentList.size(); ii++) {
                        String currentWord = currentList.get(ii);
                        pagesWithWord = index.lookUp(currentWord);

                        if (!pagesWithWord.isEmpty()) {
                            listWithListOfPagesForEachWord.add(pagesWithWord);
                        }

                        else {
                            listWithListOfPagesForEachWord.clear();
                            break;
                        }
                    }
                }

                if (!listWithListOfPagesForEachWord.isEmpty()) {

                    List<Page> listResult = listWithListOfPagesForEachWord.get(0);

                    for (int iii = 1; iii < listWithListOfPagesForEachWord.size(); iii++) {
                        List<Page> currentListOfPages = listWithListOfPagesForEachWord.get(iii);

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