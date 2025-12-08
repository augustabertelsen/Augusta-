package searchengine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TfScoringTest {

    /**
     * Verifies that, when a new object of type TfScoringTest is instantiated with
     * parameters for the word being scored
     * and the page being searched for that word, the method scoreSingleWord() is
     * called in the constructor and returns
     * a double representing the number of times the given word appears in the given
     * page when there is one match.
     */
    @Test
    void returnsWordCountWhenOneMatch() {

        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog", "cat", "mouse"));

        TfScoring scorer = new TfScoring();

        assertTrue(scorer.scoreSingleWord("dog", pageA, null, 0) == 1);
    }

    /**
     * Verifies that, when a new object of type TfScoringTest is instantiated with
     * parameters for the word being scored
     * and the page being searched for that word, the method scoreSingleWord() is
     * called in the constructor and returns
     * a double representing the number of times the given word appears in the given
     * page when there are multiple matches.
     */
    @Test
    void returnsWordCountWhenTwoMatches() {

        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog", "dog", "mouse"));

        TfScoring scorer = new TfScoring();

        assertTrue(scorer.scoreSingleWord("dog", pageA, null, 0) == 2);
    }

    /**
     * Verifies that, when a new object of type TfScoringTest is instantiated with
     * parameters for the word being scored
     * and the page being searched for that word, the method scoreSingleWord() is
     * called in the constructor and returns
     * a double representing the number of times the given word appears in the given
     * page when there are no matches.
     */
    @Test
    void returnsWordCountWhenNoMatches() {

        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog", "cat", "mouse"));

        TfScoring scorer = new TfScoring();

        assertTrue(scorer.scoreSingleWord("pig", pageA, null, 0) == 0);
    }

}
