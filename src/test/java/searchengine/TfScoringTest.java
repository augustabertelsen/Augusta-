package searchengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import main.java.searchengine.QueryHandler;
import searchengine.InvertedIndex;
import searchengine.Page;
import searchengine.TfScoring;

public class TfScoringTest {

    /**
 * Verifies that, when a new object of type TfScoringTest is instantiated with parameters for the word being scored 
 * and the page being searched for that word, the method scoreSingleWord() is called in the constructor and returns
 * a double representing the number of times the given word appears in the given page.
 */
 @Test 
    void returnsWordCount() {

        Page pageA = new Page(List.of(
            "*PAGE:http://example.com/A",
            "Title A",
            "dog", "cat", "mouse"));

        TfScoring scorer = new TfScoring();
        
        assertTrue(scorer.scoreSingleWord("dog", pageA, null, 0) == 1);
    }
    
}
