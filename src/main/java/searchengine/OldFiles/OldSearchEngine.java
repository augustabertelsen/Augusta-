/**
 * OldSearchEngine knows about all pages and decides which pages match a search word.
 * It is a search because it goes through every page and selects only those whose word list contains the (cleaned) search term.
 * 
 * INPUT:
 * - Receives one search term as a String (for example "dogs" or "Dogs!").
 *
 * PROCESS:
 * - Cleans the term:
 *   - Converts it to lowercase.
 *   - Trims extra spaces.
 *   - Removes simple punctuation at the end (for example "!" in "Dogs!").
 * - For each Page:
 *   - Looks at page.getWords().
 *   - If the cleaned term is in that word list, the Page is added to the result.
 *
 * OUTPUT:
 * - Returns a List<Page> containing only the pages that match the cleaned search term.
 *
 * EXAMPLE:
 * - Search term: " Dogs!  " → cleanSearchTerm → "dogs".
 * - Pages:
 *   - Page A words: ["cats", "dogs", "mice"]
 *   - Page B words: ["birds", "fish"]
 * - Result: only Page A is returned, because only Page A contains "dogs". 
 *
 * CHANGES FROM THE ORIGINAL VERSION:
 * - The old implementation used an InvertedIndex to find pages.
 * - Now OldSearchEngine stores a List<Page> directly and does a simple linear search
 *   over page.getWords() for the cleaned term.
 * - The cleanSearchTerm helper is kept, so the cleaning behavior (lowercase, trim,
 *   strip simple punctuation) stays the same, but the search logic is simpler and
 *   easier to understand.
 */



package searchengine.OldFiles;

import java.util.ArrayList;
import java.util.List;

import searchengine.Page;

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

    // REMOVED (commented) ORIGINAL INDEX-BASED IMPLEMENTATION:
    // public List<Page> search(String searchTerm) {
    //     String cleanSearchTerm = cleanSearchTerm(searchTerm);
    //     return index.lookUp(cleanSearchTerm);
    // }
    //
    // REMOVED: `private InvertedIndex index;` field and all javadoc that said
    // "Uses the InvertedIndex to find pages" because we now search via pages list only.
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
// }*/
