/**
 * A ScoringMethod that uses simple term frequency (TF).
 * The score for a word on a page is just the number of times
 * that word appears in the page's word list.
 * Pages where the word occurs more often get a higher score.
 */

package searchengine;

import java.util.List;

public class TfScoring implements ScoringMethod {

    /**
     * scoreSingleWord() counts how many times {@code word} appears in {@code page}.
     * 
     * @param word           the (already normalized) query word
     * @param page           the Page to score
     * @param index          the InvertedIndex (not used in this TF-only
     *                       implementation)
     * @param totalPageCount the total number of pages (not used in this TF-only
     *                       implementation)
     * @return the term frequency of {@code word} on {@code page}
     */
    @Override
    public double scoreSingleWord(String word, Page page, InvertedIndex index, int totalPageCount) {
        List<String> wordsOnPage = page.getWords();
        double TfCount = 0;
        for (String w : wordsOnPage) {
            if (w.equals(word)) {
                TfCount++;
            }
        }
        return TfCount;
    }

}
