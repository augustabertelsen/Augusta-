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
        //uses helpermethod cleanSeachTerm() that return the original searchTerm with out uppercase, whitespace and punctuation 
        String cleanSearchTerm = cleanSearchTerm(searchTerm);
        return index.lookUp(cleanSearchTerm); 
    }

        private String cleanSearchTerm(String searchTerm) {
        // change upper case letters to lowee case and removes whitespace from bothe ends of string 
        String cleanSearchTerm = searchTerm.toLowerCase().trim();
        // variable with lengt of searchword
        int wordLength = cleanSearchTerm.length();
        // variable containing last character of word
        char lastCharacter = cleanSearchTerm.charAt(wordLength-1);

        // if the last character is not a letter return cleanSeachTerme minus last character else return cleanSeachTerme
        if(!Character.isLetter(lastCharacter)){
            return cleanSearchTerm.substring(0,wordLength-1);
        } else { 
            return cleanSearchTerm;
        }
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
