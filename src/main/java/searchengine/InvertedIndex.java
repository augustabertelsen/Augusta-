// flips structure from Page retrieves word to be Word retrieves list Pages (Therefore inverted)


// Builds a fast lookup map from word into pages. 
// Each word stores the list of Pages where it appears: 
// "cat" -> [Page1, Page4, Page9]
// "java" -> [Page2]

// PageCollection loads pages -> InvertedIndex makes searching fast
// Combining would mix two different responsibilities

// Precomputes everything once so searches become instant
// Otherwise we would need to: loop through every page, loop through every word on every page, check for matches every user search (slow)


package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//when creating the InvertedIndex, it must be fully built and ready. 

public class InvertedIndex {

    //Map: Key: search word, Value: the list of pages that contain that word
    private Map<String, List<Page>> pagesContaining = new HashMap<>();

// The constructor:
    // takes the list of Pages from PageCollection
    // loops through all pages
    // loops through the pages words
    // fills the map pagesContaining
    // finish with complete index for fast lookups 

    public InvertedIndex(List<Page> allPages) {

        for (Page currentPage : allPages) {
            List<String> words = currentPage.getWords();
            for (String word : words) {

                // Creates a new list for this word because it hasn’t been seen before
                // + adds the current page to that list + stores it in the map.
                if (!pagesContaining.containsKey(word)) {
                    List<Page> newPages = new ArrayList<>();
                    newPages.add(currentPage);
                    pagesContaining.put(word, newPages);
                }

                // Retrieves the list of pages already associated with this word + adds the current page to that list.
                // Ensures that the word maps to all pages that contain it, not just the first one.
                else {
                    List<Page> existingPages = pagesContaining.get(word);
                    existingPages.add(currentPage);
                }
            }
        }
    }


    // If the word exists -> return list of its pages
    // If not -> return an empty list -> can loop safely without null-checks
    public List<Page> lookUp(String word) {
        if (pagesContaining.containsKey(word)) {
            return new ArrayList<>(pagesContaining.get(word)); // make sure to return copy of lists so the originals cant be modified by other classes
        }
        return new ArrayList<>();
    }



    // for more words use method retain all 
}
