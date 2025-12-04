
/**
 * Builds and stores a fast lookup structure that maps each normalized word
 * to the list of pages containing the word(s). All words in all pages are
 * cleaned using WordNormalizer, and the internal index records each
 * page under its normalized terms. Calling lookUp(word) normalizes
 * the input and returns all pages that contain the word(s), avoiding the need
 * to scan every page on each search.
 *
 * Input:  a list of Page objects (allPages).  
 * Output: a list of pages containing the normalized search word(s).
 */

package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndex {
    private final Map<String, List<Page>> pagesContaining;
    private final int totalPageCount;

    public InvertedIndex(List<Page> allPages) {
        totalPageCount = allPages.size();
        pagesContaining = new HashMap<>();
        buildIndex(allPages);

    }

    private void buildIndex(List<Page> allPages) {

        for (Page currentPage : allPages) {
            addPageToIndex(currentPage);
        }
    }

    private void addPageToIndex(Page page) {
        List<String> words = page.getTotalWords();
        for (String rawWord : words) {
            String normalized = WordNormalizer.normalize(rawWord);
            if (normalized.isEmpty()) {
                continue;
            }
            List<Page> pagesForWord = pagesContaining.get(normalized);
            if (pagesForWord == null) {
                pagesForWord = new ArrayList<>(); 
                pagesContaining.put(normalized, pagesForWord);
            }
            if (!pagesForWord.contains(page)) {
                pagesForWord.add(page);
            }
        }
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public List<Page> lookUp(String word) {
        String normalized = WordNormalizer.normalize(word);
        if (normalized.isEmpty()) {
            return new ArrayList<>();
        }
        List<Page> pagesForWord = pagesContaining.get(normalized);
        if (pagesForWord == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(pagesForWord);
    }

}
