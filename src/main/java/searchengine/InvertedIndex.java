// Stores, in pagesContaining, which Page objects contain which cleaned word. It remembers, 
// for each cleaned word, which Page objects contain that word.   It goes through every Page in
// allPages, cleans every word with WordNormalizer, and fills the pagesContaining map; later 
// lookUp(word) cleans the input word and returns the Pages that match, so SearchEngine can 
// very quickly find all pages that contain a given word, instead of scanning every page every time.
// Input: List<Page> allPages from PageCollection.
// Output: A List<Page> of pages that contain the cleaned word.


package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InvertedIndex {

    //Map: Key: search word, Value: the list of pages that contain that word
    private Map<String, List<Page>> pagesContaining;


    public InvertedIndex(List<Page> allPages) {
        pagesContaining = new HashMap<>();
        buildIndex(allPages);

    }

    private void buildIndex(List<Page> allPages){
        for(Page currentPage: allPages){
        addPageToIndex(currentPage);
        }
    }

    private void addPageToIndex(Page page){
        List<String> words = page.getWords();
        for (String rawWord : words) {
            String normalized = WordNormalizer.normalize(rawWord);
            if (normalized.isEmpty()) {
                continue;
             }
             List<Page> pagesForWord = pagesContaining.get(normalized);
             if(pagesForWord == null){
                pagesForWord = new ArrayList<Page>();
                pagesContaining.put(normalized, pagesForWord);
             }
             if (!pagesForWord.contains(page)) {
                pagesForWord.add(page);
             }
        }
    }

    public List<Page> lookUp(String word){
        String normalized = WordNormalizer.normalize(word);
        if(normalized.isEmpty()){
            return new ArrayList<>();
        }
        List<Page> pagesForWord  = pagesContaining.get(normalized);
        if (pagesForWord == null){
            return new ArrayList<>();
        }
        return new ArrayList<>(pagesForWord);
    }

}




//         for (Page currentPage : allPages) {
//             List<String> words = currentPage.getWords();
//             for (String word : words) {
//             }
//                 // Creates a new list for this word because it hasn’t been seen before
//                 // + adds the current page to that list + stores it in the map.
//                 if (!pagesContaining.containsKey(word)) {
//                     List<Page> newPages = new ArrayList<>();
//                     newPages.add(currentPage);
//                     pagesContaining.put(word, newPages);
//                 }

//                 // Retrieves the list of pages already associated with this word + adds the current page to that list.
//                 // Ensures that the word maps to all pages that contain it, not just the first one.
//                 else {
//                     List<Page> existingPages = pagesContaining.get(word);
//                     existingPages.add(currentPage);
//                 }
//             }
        
    


//     // If the word exists -> return list of its pages
//     // If not -> return an empty list -> can loop safely without null-checks
//     public List<Page> lookUp(String word) {
//         if (pagesContaining.containsKey(word)) {
//             return new ArrayList<>(pagesContaining.get(word)); // make sure to return copy of lists so the originals cant be modified by other classes
//         }
//         return new ArrayList<>();
//     }



//     // for more words use method retain all 
