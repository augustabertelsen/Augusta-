/**

 * This is a JavaDoc comment.

 */
// Provides the search logic + Decides how to use invertedIndexs data structure to preform searches 
// Uses the InvertedIndex to find pages matching a search term

// Middle layer between webServer/QueryHandler (gets users search word) and InvertedIndex (know which page the word belongs to)

package searchengine;

import java.util.List;

public class SearchEngine {
    // uses to find pages fast
    private InvertedIndex index;

    // // provides access to all pages and their raw data (maybe remove)
    // private PageCollection collection;

    // Constructor builds the search engine -> DOES NOT perform search
    // Needs InvertedIndex to perfom lookups
    public SearchEngine(InvertedIndex index) {
        this.index = index;
    }

    public List<Page> search(String searchTerm) {
        // String cleanSearchTerm = cleanTerm(searchTerm);
        return index.lookUp(searchTerm); // change to (cleanSearchTerm)
    }


    // Helper method - NOT DONE
    private String cleanTerm(String searchTerm) {
        // to lowercase
        String seachTermLowerCase = seachTerm.toLowerCase();
        // trim
        // remove puncuation
        // return clean String
    }

}

// Code from OldWebServer
// // search algorithm:
// List<List<String>> search(String searchTerm) {
// var result = new ArrayList<List<String>>();
// for (var page : pages) {
// if (page.contains(searchTerm)) {
// result.add(page);
// }
// }
// return result;
// }
