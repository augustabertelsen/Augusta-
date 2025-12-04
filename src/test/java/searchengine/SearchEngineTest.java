package searchengine;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SearchEngineTest {

    /**
     * Tests that a single-word query returns pages containing that word
     * and does not return pages that do not contain it.
     */
    @Test
    void searchSingleWordMatchesPages() {
        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog"));
        Page pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "cat"));

        List<Page> allPages = List.of(pageA, pageB);
        SearchEngine searchEngine = new SearchEngine(new InvertedIndex(allPages),
                new Ranking(new InvertedIndex(allPages), false));

        List<List<String>> termList = List.of(List.of("dog"));

        List<Page> result = searchEngine.search(termList);

        assertTrue(result.contains(pageA));
        assertFalse(result.contains(pageB));
    }

    /**
     * Tests that a query with multiple words in the same group is treated
     * as an AND-query, so only pages containing all those words are returned.
     */
    @Test
    void searchMultipleWords_and() {
        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog park"));
        Page pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "cat park"));
        Page pageC = new Page(List.of(
                "*PAGE:http://example.com/C",
                "Title C",
                "dog at beach"));

        List<Page> allPages = List.of(pageA, pageB, pageC);
        SearchEngine searchEngine = new SearchEngine(new InvertedIndex(allPages),
                new Ranking(new InvertedIndex(allPages), false));

        List<List<String>> termList = List.of(List.of("dog", "park")); // dog AND park
        List<Page> result = searchEngine.search(termList);

        assertTrue(result.contains(pageA));
        assertFalse(result.contains(pageB));

    }

    /**
     * Tests that multiple OR-groups are combined correctly, so pages
     * matching any of the groups (dog OR cat) are returned.
     */
    @Test
    void searchMultipleWords_or() {
        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog park"));
        Page pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "cat park"));
        Page pageC = new Page(List.of(
                "*PAGE:http://example.com/C",
                "Title C",
                "dog at beach"));

        List<Page> allPages = List.of(pageA, pageB, pageC);
        SearchEngine searchEngine = new SearchEngine(new InvertedIndex(allPages),
                new Ranking(new InvertedIndex(allPages), false));

        List<List<String>> termList = List.of( // dog OR
                List.of("dog"),
                List.of("cat"));

        List<Page> result = searchEngine.search(termList);

        assertTrue(result.contains(pageA));
        assertTrue(result.contains(pageB));
        assertTrue(result.contains(pageC));
    }

    /**
     * Tests that combining AND inside groups and OR between groups produces
     * exactly the pages that satisfy at least one full group of terms.
     */
    @Test
    void searchMultipleWords_and_or() {
        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog park"));
        Page pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "cat park"));
        Page pageC = new Page(List.of(
                "*PAGE:http://example.com/C",
                "Title C",
                "dog at beach"));

        List<Page> allPages = List.of(pageA, pageB, pageC);
        SearchEngine searchEngine = new SearchEngine(new InvertedIndex(allPages),
                new Ranking(new InvertedIndex(allPages), false));

        List<List<String>> termList = List.of( // dog OR
                List.of("dog", "park"),
                List.of("cat"));

        List<Page> result = searchEngine.search(termList);

        assertTrue(result.contains(pageA));
        assertTrue(result.contains(pageB));
        assertFalse(result.contains(pageC));
    }

    /**
     * Tests that searching for a word that does not appear in any indexed page
     * results in an empty result list.
     */
    @Test
    void searchNoMatchReturnsEmptyList() {
        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog"));

        List<Page> allPages = List.of(pageA);
        SearchEngine searchEngine = new SearchEngine(new InvertedIndex(allPages),
                new Ranking(new InvertedIndex(allPages), false));

        List<List<String>> termList = List.of(List.of("sun"));
        List<Page> result = searchEngine.search(termList);

        assertFalse(result.contains(pageA));
    }

    // @Test
    // void handleSearchHandlesHTTP() {
    // List<Page> allPages = new ArrayList<>();
    // SearchEngine searchEngine = new SearchEngine(new InvertedIndex(allPages),
    // new Ranking(new InvertedIndex(allPages), false));

    // HttpExchange exchange = new HttpExchange();

    // }

    // handleSearchUsesRankingOrder – Ranking to result give same answer

    /**
     * Tests that finalResultBuilder creates a JSON array with the correct
     * structure, order, and page data (URL and title for each Page).
     */
    @Test
    void finalResultBuilderJsonFormat() {
        Page pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "first"));
        Page pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "second"));

        List<Page> allPages = List.of(pageA, pageB);
        SearchEngine searchEngine = new SearchEngine(new InvertedIndex(allPages),
                new Ranking(new InvertedIndex(allPages), false));

        byte[] bytes = searchEngine.finalResultBuilder(allPages);
        String json = new String(bytes, StandardCharsets.UTF_8);

        assertTrue(json.startsWith("["));
        assertTrue(json.endsWith("]"));

        String jsonBody = json.substring(1, json.length() - 1);
        String[] pageObjects = jsonBody.split("},\\s*\\{");

        String fristPageObject = "{" + pageObjects[0] + "}";
        String secondPageObject = "{" + pageObjects[1] + "}";

        assertTrue(fristPageObject.contains(pageA.getUrl()));
        assertTrue(fristPageObject.contains(pageA.getTitle()));

        assertTrue(secondPageObject.contains(pageA.getUrl()));
        assertTrue(secondPageObject.contains(pageA.getTitle()));
    }



      /**
     * Verifies that lookUp() can handle a multi-word search and
     * still return the page that contains the full sequence of words.
     * Does not take normalization or multiple pages into account.
     */

//     @Test
//     void lookUpMultipleWords() {
//         Page pageA = new Page(List.of(
//                 "*PAGE:http://example.com/A",
//                 "Title A",
//                 "walking the dog", "the president is in the goverment", "laughing a lot"));

//         List<Page> allPages = List.of(pageA);
//         InvertedIndex index = new InvertedIndex(allPages);

//         List<Page> result = index.lookUp("the president is in the goverment");

//         assertTrue(result.contains(pageA));
//         assertEquals(1, result.size());
//     }

}
