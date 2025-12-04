package searchengine;

/**
 * Interface for scoring a single word on a single page. Different implementations 
 * that {@link TfScoring} and {@link TfIdfScoring} can use the same method signature 
 * but return scores in different ways.
 */

public interface ScoringMethod {

    /**
     * Calculates a score for one word on one page.
     *
     * @param word           the query word
     * @param page           the page to score
     * @param index          the inverted index 
     * @param totalPageCount the total number of pages in the collection
     * @return the score for this (word, page) pair
     */
    double scoreSingleWord(String word, Page page, InvertedIndex index, int totalPageCount);

}
