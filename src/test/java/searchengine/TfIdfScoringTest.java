package searchengine;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
//import searchengine.TfScoring;

public class TfIdfScoringTest {

    /*@BeforeAll
    public static void setup() {

        Page pageA = new Page(List.of(
            "*PAGE:http://example.com/A",
            "Title A",
            "dog", "president", "laugh"
        ));
        Page pageB = new Page(List.of(
            "*PAGE:http://example.com/B",
            "Title B",
            "king", "denmark", "dog"
        ));
        Page pageC = new Page(List.of(
            "*PAGE:http://example.com/C",
            "Title C",
            "fun", "laugh"));

        List<Page> allPages = Arrays.asList(pageA, pageB, pageC);
        InvertedIndex index = new InvertedIndex(allPages);
        TfIdfScoring scorer = new TfIdfScoring();}*/

    /**
     * When the scoreSingleWord() method is paramaterized with a word, a page, an index and the total
     * number of pages in a collection, it returns the number of times the word appears on that page multiplied by
     * the inverse frequency of that word throughout the collection. This verifies that it works when there is one
     * instance of the given word on the given page, and one other instance of that word on one other page in the
     * collection.
     */

    @Test
    public void returnsCorrectlyWhenOneMatchAndTwoFrequencies() {

        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog", "president", "laugh"
        ));
        Page pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "king", "denmark", "dog"
        ));
        Page pageC = new Page(List.of(
                "*PAGE:http://example.com/C",
                "Title C",
                "fun", "laugh"));

        List<Page> allPages = Arrays.asList(pageA, pageB, pageC);
        InvertedIndex index = new InvertedIndex(allPages);
        TfIdfScoring scorer = new TfIdfScoring();

        assertTrue(scorer.scoreSingleWord("dog", pageA, index, 3) == 1.5);
    }

    /**
     * Verifies that the method returns correctly works when there is one instance of the
     * given word on the given page and no other instances of that word on any other page
     * in the collection.
     */

    @Test
    public void returnsCorrectlyWhenOneMatchAndOneFrequency() {
        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog", "president", "laugh"
        ));
        Page pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "king", "denmark", "dog"
        ));
        Page pageC = new Page(List.of(
                "*PAGE:http://example.com/C",
                "Title C",
                "fun", "laugh"));

        List<Page> allPages = Arrays.asList(pageA, pageB, pageC);
        InvertedIndex index = new InvertedIndex(allPages);
        TfIdfScoring scorer = new TfIdfScoring();

        assertTrue(scorer.scoreSingleWord("denmark", pageB, index, 3) == 3.0);
    }

    /**
     * Verifies that the method returns correctly when there are no instances of the given word on
     * the given page but one instance of that word on another page in the collection.
     */

    @Test
    public void returnsCorrectlyWhenNoMatchAndOneFrequency() {
        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog", "president", "laugh"
        ));
        Page pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "king", "denmark", "dog"
        ));
        Page pageC = new Page(List.of(
                "*PAGE:http://example.com/C",
                "Title C",
                "fun", "laugh"));

        List<Page> allPages = Arrays.asList(pageA, pageB, pageC);
        InvertedIndex index = new InvertedIndex(allPages);
        TfIdfScoring scorer = new TfIdfScoring();

        assertTrue(scorer.scoreSingleWord("king", pageC, index, 3) == 0.0);
    }

    /**
     * Verifies that the method returns correctly when there are no instances of the given word on
     * the given page and no other instances of that word on any other page in the collection.
     */

    @Test
    public void returnsCorrectlyWhenNoMatchAndNoFrequency() {
        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog", "president", "laugh"
        ));
        Page pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "king", "denmark", "dog"
        ));
        Page pageC = new Page(List.of(
                "*PAGE:http://example.com/C",
                "Title C",
                "fun", "laugh"));

        List<Page> allPages = Arrays.asList(pageA, pageB, pageC);
        InvertedIndex index = new InvertedIndex(allPages);
        TfIdfScoring scorer = new TfIdfScoring();

        assertTrue(scorer.scoreSingleWord("queen", pageA, index, 3) == 0.0);
    }

}
