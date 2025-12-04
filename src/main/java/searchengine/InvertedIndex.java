
package searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds and stores a fast lookup structure from normalized words to the pages that
 * contain them. All title and content words from each {@link Page} are passed through
 * {@link WordNormalizer} and inserted into a map of {@code word -> list of pages}.
 * The search engine can then use {@link #lookUp(String)} to find matching pages
 * without scanning every page on each query.
 */

public class InvertedIndex {
    private final Map<String, List<Page>> pagesContaining;
    private final int totalPageCount;

    /**
     * Creates a new inverted index from a list of pages. All words from the given
     * pages are normalized and added to the internal map so that later lookups can
     * be done efficiently.
     *
     * @param allPages the collection of pages that should be indexed
     */
    public InvertedIndex(List<Page> allPages) {
        totalPageCount = allPages.size();
        pagesContaining = new HashMap<>();
        buildIndex(allPages);

    }

    /**
     * Builds the internal word-to-pages map from all pages in the collection.
     *
     * @param allPages the pages that should be added to the index
     */
    private void buildIndex(List<Page> allPages) {

        for (Page currentPage : allPages) {
            addPageToIndex(currentPage);
        }
    }

    /**
     * Adds a single page to the index by inserting all its normalized words
     * into the {@code pagesContaining} map.
     *
     * @param page the page whose words should be indexed
     */
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

    /**
     * Looks up all pages that contain the given word. The input word is normalized
     * using
     * {@link WordNormalizer}. If the normalized word is empty or not present in the
     * index,
     * an empty list is returned. Otherwise a new list containing all matching pages
     * is returned.
     *
     * @param word the raw query word to search for
     * @return a list of pages that contain the normalized form of the word
     */
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
