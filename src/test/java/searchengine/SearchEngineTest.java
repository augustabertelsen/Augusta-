package searchengine;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

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
                InvertedIndex index = new InvertedIndex(allPages);
                Ranking ranking = new Ranking(index, false);
                SearchEngine searchEngine = new SearchEngine(index, ranking);

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
                InvertedIndex index = new InvertedIndex(allPages);
                Ranking ranking = new Ranking(index, false);
                SearchEngine searchEngine = new SearchEngine(index, ranking);

                List<List<String>> termList = List.of(List.of("dog", "park")); // dog AND park
                List<Page> result = searchEngine.search(termList);

                assertTrue(result.contains(pageA));
                assertFalse(result.contains(pageB));
                assertFalse(result.contains(pageC));
                assertEquals(1, result.size());

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
                InvertedIndex index = new InvertedIndex(allPages);
                Ranking ranking = new Ranking(index, false);
                SearchEngine searchEngine = new SearchEngine(index, ranking);

                List<List<String>> termList = List.of( // dog OR
                                List.of("dog"),
                                List.of("cat"));

                List<Page> result = searchEngine.search(termList);

                assertTrue(result.contains(pageA));
                assertTrue(result.contains(pageB));
                assertTrue(result.contains(pageC));
                assertEquals(3, result.size());
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
                InvertedIndex index = new InvertedIndex(allPages);
                Ranking ranking = new Ranking(index, false);
                SearchEngine searchEngine = new SearchEngine(index, ranking);

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
                InvertedIndex index = new InvertedIndex(allPages);
                Ranking ranking = new Ranking(index, false);
                SearchEngine searchEngine = new SearchEngine(index, ranking);

                List<List<String>> termList = List.of(List.of("sun"));
                List<Page> result = searchEngine.search(termList);

                assertFalse(result.contains(pageA));
                assertEquals(0, result.size());
        }

        /**
         * Verifies that finalResultBuilder produces a JSON array string with the expected
         * structure and that each array element contains the correct URL and title
         * for the corresponding Page in order.
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
                InvertedIndex index = new InvertedIndex(allPages);
                Ranking ranking = new Ranking(index, false);
                SearchEngine searchEngine = new SearchEngine(index, ranking);

                byte[] bytes = searchEngine.finalResultBuilder(allPages);
                String json = new String(bytes, StandardCharsets.UTF_8);

                assertTrue(json.startsWith("["));
                assertTrue(json.endsWith("]"));

                String jsonBody = json.substring(1, json.length() - 1);
                String[] pageObjects = jsonBody.split("},\\s*\\{");

                assertEquals(2, pageObjects.length);

                String fristPageObject = "{" + pageObjects[0] + "}";
                String secondPageObject = "{" + pageObjects[1] + "}";

                assertTrue(fristPageObject.contains(pageA.getUrl()));
                assertTrue(fristPageObject.contains(pageA.getTitle()));

                assertTrue(secondPageObject.contains(pageB.getUrl()));
                assertTrue(secondPageObject.contains(pageB.getTitle()));
        }

        /**
        * Verifies that handleSearch reads the HTTP query parameter, performs a search,
         * and returns JSON bytes containing the matching Page's URL and title.
         */
        @Test
        void handleSearchReturnsJSONforHTTPQuery() {
                Page pageA = new Page(List.of(
                                "*PAGE:http://example.com/A",
                                "Title A",
                                "dog"));
                List<Page> allPages = List.of(pageA);
                InvertedIndex index = new InvertedIndex(allPages);
                Ranking ranking = new Ranking(index, false);
                SearchEngine searchEngine = new SearchEngine(index, ranking);

                 HttpExchange exchange = new HttpExchange() {
                 @Override
                public URI getRequestURI() {
                        return URI.create("http://localhost/search?q=dog");
                }

                @Override public Headers getRequestHeaders() { return new Headers(); }
                @Override public Headers getResponseHeaders() { return new Headers(); }
                @Override public String getRequestMethod() { return "GET"; }
                @Override public HttpContext getHttpContext() { return null; }
                @Override public void close() { }
                @Override public InputStream getRequestBody() { return InputStream.nullInputStream(); }
                @Override public OutputStream getResponseBody() { return OutputStream.nullOutputStream(); }
                @Override public void sendResponseHeaders(int rCode, long responseLength) { }
                @Override public InetSocketAddress getRemoteAddress() { return null; }
                @Override public int getResponseCode() { return 200; }
                @Override public InetSocketAddress getLocalAddress() { return null; }
                @Override public String getProtocol() { return "HTTP/1.1"; }
                @Override public Object getAttribute(String name) { return null; }
                @Override public void setAttribute(String name, Object value) { }
                @Override public void setStreams(InputStream i, OutputStream o) { }
                @Override public HttpPrincipal getPrincipal() { return null; }
                };

                byte[] bytes = searchEngine.handleSearch(exchange);
                String json = new String(bytes, StandardCharsets.UTF_8);

                assertTrue(json.startsWith("["));
                assertTrue(json.endsWith("]"));
                assertTrue(json.contains(pageA.getUrl()));
                assertTrue(json.contains(pageA.getTitle()));

        }

}
