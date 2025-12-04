
package searchengine;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class TfIdfScoringTest {
    static TfIdfScoring scorer;
    static InvertedIndex index;
    static Page pageA, pageB, pageC;
    static List<Page> allPages;

    @BeforeAll
    static void setUp() {
        pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog", "president", "laugh"));
        pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "king", "denmark", "dog"));
        pageC = new Page(List.of(
                "*PAGE:http://example.com/C",
                "Title C",
                "fun", "laugh", "fun"));

        allPages = Arrays.asList(pageA, pageB, pageC);
        index = new InvertedIndex(allPages);
        scorer = new TfIdfScoring();
    }

    /**
     * When the scoreSingleWord() method is paramaterized with a word, a page, an
     * index and the total
     * number of pages in a collection, it returns the number of times the word
     * appears on that page multiplied by
     * the inverse frequency of that word throughout the collection. This verifies
     * that it works when there is one
     * instance of the given word on the given page, and one other instance of that
     * word on one other page in the
     * collection.
     */

    @Test
    void returnsCorrectlyWhenOneMatchAndTwoFrequencies() {
        String wordToTest = "dog";
        double wordOccurrencesInPageA = 1;
        double countOfPagesContainingWord = 2;
        
        double expectedScore = wordOccurrencesInPageA * Math.log(allPages.size() / countOfPagesContainingWord);
        assertEquals(expectedScore, scorer.scoreSingleWord(wordToTest, pageA, index, allPages.size()));
    }

    /**
     * Verifies that the method returns correctly works when there is one instance
     * of the
     * given word on the given page and no other instances of that word on any other
     * page
     * in the collection.
     */

    @Test
    void returnsCorrectlyWhenOneMatchAndOneFrequency() {
        String wordToTest = "denmark";
        double wordOccurrencesInPageB = 1;
        double countOfPagesContainingWord = 1;
        
        double expectedScore = wordOccurrencesInPageB * Math.log(allPages.size() / countOfPagesContainingWord);
        assertEquals(expectedScore, scorer.scoreSingleWord(wordToTest, pageB, index, allPages.size()));
    }

    /**
     * Verifies that the method returns correctly when there are no instances of the
     * given word on
     * the given page but one instance of that word on another page in the
     * collection.
     */

    @Test
    void returnsCorrectlyWhenNoMatchAndOneFrequency() {
        assertEquals(0.0, scorer.scoreSingleWord("king", pageC, index, allPages.size()));
    }

    /**
     * Verifies that the method returns correctly when there are no instances of the
     * given word on
     * the given page and no other instances of that word on any other page in the
     * collection.
     */

    @Test
    void returnsCorrectlyWhenNoMatchAndNoFrequency() {
        assertEquals(0.0, scorer.scoreSingleWord("queen", pageA, index, allPages.size()));
    }

     /**
     * Verifies that the method returns correctly when there are two instances of the
     * given word on
     * the given page and no other instances of that word on any other page in the
     * collection.
     */

    @Test
    void returnsCorrectlyWhenTwoMatchAndOneFrequency() {
        String wordToTest = "fun";
        double wordOccurrencesInPageC = 2;
        double countOfPagesContainingWord = 1;

        double expectedScore = wordOccurrencesInPageC * Math.log(allPages.size() / countOfPagesContainingWord);
        assertEquals(expectedScore, scorer.scoreSingleWord(wordToTest, pageC, index, allPages.size()));
    }


}
