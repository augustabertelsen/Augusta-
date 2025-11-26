/**
 * Knows about all pages and finds the ones that match a search word.
 * <p>
 * INPUT: a search term as a String (for example "dogs").
 * SearchEngine cleans the term (lowercase, remove simple punctuation), looks through
 * every Page it knows, and picks the pages that contain that word.
 * OUTPUT: a List<Page> containing only the pages that match the search term.
 */

package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * CHANGED: Updated description to match new behavior (no inverted index).
 * Knows about a list of pages and can find pages that match a single search word.
 * INPUT: a raw search term like "Dogs!".
 * OUTPUT: a List<Page> that contains only the pages whose words contain "dogs".
 */
// CHANGED: We keep the class name SearchEngine; this replaces OldSearchEngine’s behavior.
public class OldSearchEngine {

    // CHANGED: Instead of an InvertedIndex, we now store all pages directly.
    private final List<Page> pages;

    // REMOVED 
    // private InvertedIndex index;

    // REMOVED: Old unused PageCollection comment/field, since we only need List<Page>.
    // // provides access to all pages and their raw data (maybe remove)
    // // private PageCollection collection;

    // CHANGED: Constructor now gets the list of pages we will search through.

    public OldSearchEngine(List<Page> pages) {
        // CHANGED: Store pages, defensively copying into a new ArrayList so caller’s list can’t be mutated underneath us.
        this.pages = new ArrayList<>(pages);
    }

    // CHANGED: Keep the method name 'search', but implement it as a linear scan over pages.
    // INPUT: searchTerm as a String (e.g. "Dogs!").
    // OUTPUT: all pages whose words contain the cleaned search term.
    public List<Page> search(String searchTerm) {
        // CHANGED: Reuse the old helper: clean the term (lowercase, trim, strip trailing punctuation).
        String cleanSearchTerm = cleanSearchTerm(searchTerm);

        // ADDED: Create result list to collect matching pages.
        List<Page> result = new ArrayList<>();

        // ADDED: Loop through every page we know.
        for (Page page : pages) {
            // ADDED: Get the words from the page and check if they contain the cleaned term.
            if (page.getWords().contains(cleanSearchTerm)) {
                // ADDED: If yes, add page to the result list.
                result.add(page);
            }
        }

        // ADDED: Return all matching pages.
        return result;
    }

    // CHANGED: Keep the old cleanSearchTerm logic as-is, because it is useful.
    // It turns things like " Dogs! " into "dogs".
    private String cleanSearchTerm(String searchTerm) {
        // CHANGED: Same behavior as before – lower case and trim whitespace.
        String cleanSearchTerm = searchTerm.toLowerCase().trim();

        // CHANGED: If the cleaned term is now empty, just return it to avoid charAt errors.
        if (cleanSearchTerm.isEmpty()) {
            return cleanSearchTerm;
        }

        // CHANGED: Compute length and last character.
        int wordLength = cleanSearchTerm.length();
        char lastCharacter = cleanSearchTerm.charAt(wordLength - 1);

        // CHANGED: If last char is not a letter, strip it off; otherwise return as-is.
        if (!Character.isLetter(lastCharacter)) {
            return cleanSearchTerm.substring(0, wordLength - 1);
        } else {
            return cleanSearchTerm;
        }
    }

    // REMOVED: Old version that depended on InvertedIndex.
    // public List<Page> search(String searchTerm) {
    //     String cleanSearchTerm = cleanSearchTerm(searchTerm);
    //     return index.lookUp(cleanSearchTerm);
    // }

 
}






// OldSearchEngine

// public class OldSearchEngine {
//     // uses to find pages fast
//     private InvertedIndex index;

//     // // provides access to all pages and their raw data (maybe remove)
//     // private PageCollection collection;

//     // Constructor builds the search engine -> DOES NOT perform search
//     // Needs InvertedIndex to perfom lookups
//     public OldSearchEngine(InvertedIndex index) {
//         this.index = index;
//     }

//     public List<Page> search(String searchTerm) {
//         //uses helpermethod cleanSeachTerm() that return the original searchTerm with out uppercase, whitespace and punctuation 
//         String cleanSearchTerm = cleanSearchTerm(searchTerm);
//         return index.lookUp(cleanSearchTerm); 
//     }

//         private String cleanSearchTerm(String searchTerm) {
//         // change upper case letters to lowee case and removes whitespace from bothe ends of string 
//         String cleanSearchTerm = searchTerm.toLowerCase().trim();
//         // variable with lengt of searchword
//         int wordLength = cleanSearchTerm.length();
//         // variable containing last character of word
//         char lastCharacter = cleanSearchTerm.charAt(wordLength-1);

//         // if the last character is not a letter return cleanSeachTerme minus last character else return cleanSeachTerme
//         if(!Character.isLetter(lastCharacter)){
//             return cleanSearchTerm.substring(0,wordLength-1);
//         } else { 
//             return cleanSearchTerm;
//         }
//     }

// }

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
