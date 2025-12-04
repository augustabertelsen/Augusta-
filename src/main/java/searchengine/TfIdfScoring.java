
package searchengine;

import java.util.List;

/**
 * A {@link ScoringMethod} implementation that uses TF‑IDF
 * (term frequency - inverse document frequency).
 * Words that occur many times on a page but in few pages overall
 * receive higher scores.
 */

public class TfIdfScoring implements ScoringMethod {

    /**
     * Calculates the TF‑IDF score for one word on one page.
     * Counts how often {@code word} appears on {@code page} (TF),
     * finds how many pages in {@code index} contain the word (DF),
     * computes an IDF value from {@code totalPageCount} and DF,
     * and returns {@code TF * IDF}. If the word is missing from the
     * page or the index, {@code 0.0} is returned.
     *
     * @param word           the query word
     * @param page           the page to score
     * @param index          the inverted index used to count how many pages contain
     *                       the word
     * @param totalPageCount the total number of pages in the collection
     * @return the TF‑IDF score for this word on this page
     */

    @Override
    public double scoreSingleWord(String word, Page page, InvertedIndex index, int totalPageCount) {
        List<String> wordsOnPage = page.getTotalWords();
        double tf = 0;
        for (String w : wordsOnPage) {
            if (w.equals(word)) {
                tf++;
            }
        }
        if (tf == 0) {
            return 0.0;
        }

        List<Page> pagesContaining = index.lookUp(word);
        double df = pagesContaining.size();
        if (df == 0) {
            return 0.0;
        }

        double idf = Math.log(totalPageCount / df);
        double tfidf = tf * idf;

        return tfidf;
    }

}
