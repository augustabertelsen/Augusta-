package searchengine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link InvertedIndex}, covering index building,
 * normalization, lookups, and empty-result behavior.
 */

public class InvertedIndexTest {

        /**
         * Verifies that looking up a simple, lowercase word with no punctuation
         * returns exactly the single page that contains that word.
         * Ensures basic matching works when no normalization, multiple pages,
         * multiple search words challenges are present.
         */

        @Test
        void lookUpSingleWordSinglePage() {
                Page pageA = new Page(List.of(
                                "*PAGE:http://example.com/A",
                                "Title A",
                                "government, president"));

                List<Page> allPages = List.of(pageA);
                InvertedIndex index = new InvertedIndex(allPages);

                List<Page> result = index.lookUp("government");

                assertTrue(result.size() == 1);
                assertTrue(result.contains(pageA));
        }

        /**
         * Verifies that when searching for one word across multiple pages,
         * lookUp returns only the pages that contain that word.
         * Confirms correct filtering and excludes pages without the word.
         */

        @Test
        void lookUpWordInMultiplePages() {
                Page pageA = new Page(List.of(
                                "*PAGE:http://example.com/A",
                                "Title A",
                                "dog", "president", "laugh"));
                Page pageB = new Page(List.of(
                                "*PAGE:http://example.com/B",
                                "Title B",
                                "king", "denmark", "dog"));
                Page pageC = new Page(List.of(
                                "*PAGE:http://example.com/C",
                                "Title C",
                                "fun", "laugh"));

                List<Page> allPages = List.of(pageA, pageB, pageC);
                InvertedIndex index = new InvertedIndex(allPages);

                List<Page> result = index.lookUp("laugh");

                assertTrue(result.contains(pageA));
                assertTrue(result.contains(pageC));
                assertFalse(result.contains(pageB));
                assertEquals(2, result.size());
        }

        /**
         * Verifies that lookUp() correctly normalizes the search word
         * (case, punctuation) and still returns the page containing
         * the matching normalized term.
         */

        @Test
        void lookUpNormalizedWord() {
                Page pageA = new Page(List.of(
                                "*PAGE:http://example.com/A",
                                "Title A",
                                "Government, President, USA"));

                List<Page> allPages = List.of(pageA);
                InvertedIndex index = new InvertedIndex(allPages);

                List<Page> result1 = index.lookUp("PRESIDENT!");
                List<Page> result2 = index.lookUp("president,");

                assertEquals(1, result1.size());
                assertTrue(result1.contains(pageA));

                assertEquals(1, result2.size());
                assertTrue(result2.contains(pageA));
        }

        /**
         * Verifies that lookUp() returns an empty list (not null)
         * when searching for a word that appears in no pages.
         * Does not take normalization, multiple pages or multiple
         * words into account.
         */

        @Test
        void lookUpReturnEmptyPageForUnknownWord() {
                Page pageA = new Page(List.of(
                                "*PAGE:http://example.com/A",
                                "Title A",
                                "government"));

                List<Page> allPages = List.of(pageA);
                InvertedIndex index = new InvertedIndex(allPages);

                List<Page> result = index.lookUp("laugh");

                assertNotEquals(null, result);
                assertEquals(0, result.size());
        }

        /**
         * Verifies that lookUp() returns a page only once,
         * even if the searched word appears multiple times within that page.
         */

        @Test
        void lookUpReturnsOnePageForRepeatedWord() {
                Page pageA = new Page(List.of(
                                "*PAGE:http://example.com/A",
                                "Title A",
                                "government", "government", "president"));

                List<Page> allPages = List.of(pageA);
                InvertedIndex index = new InvertedIndex(allPages);

                List<Page> result = index.lookUp("government");

                assertTrue(result.contains(pageA));
                assertEquals(1, result.size());
        }
}
