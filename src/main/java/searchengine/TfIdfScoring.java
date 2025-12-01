/**
 * A ScoringMethod that uses TF-IDF (term frequency – inverse document frequency).
 * This class gives a score to one word on one page.
 * The score becomes larger when:
 *   the word appears many times on the page (high term frequency), and
 *   the word appears in few pages in total (high inverse document frequency).
 * This means the score is high for words that are important for this page,
 * but not very common in the whole collection.
 */

package searchengine;

import java.util.List;

// TF: how many times the word appears on this page
// IDF: how rare the word is across all pages

public class TfIdfScoring implements ScoringMethod{


/**
 * Calculates the TF-IDF score for one word on one page.
 * First it counts how many times {@code word} appears in {@code page}.
 * Then it finds how many pages in {@code index} contain {@code word}.
 * Using the total number of pages {@code totalPageCount}, it calculates
 * an inverse document frequency value.
 * The final score is the term frequency multiplied by this inverse document
 * frequency.
 * If the word does not appear in the page, or in any page in the index,
 * this method returns {@code 0.0}.
 *
 * @param word           the (already normalized) query word
 * @param page           the Page to score
 * @param index          the InvertedIndex used to count how many pages contain the word
 * @param totalPageCount the total number of pages in the collection
 * @return the TF-IDF score for this word on this page
 */


    @Override
    public double scoreSingleWord(String word, Page page, InvertedIndex index, int totalPageCount) {
        List<String> wordsOnPage = page.getWords();
        double tf = 0;
        for(String w : wordsOnPage){
            if(w.equals(word)){
                tf++;
            }
        }
        if(tf == 0){
            return 0.0;
        }

        List<Page> pagesContaining = index.lookUp(word);
        double df = pagesContaining.size();
        if(df == 0){
            return 0.0;
        }
    
        double idf = Math.log(totalPageCount/df);
        double tfidf = tf * idf;
        
        return tfidf;
    }
    
}
