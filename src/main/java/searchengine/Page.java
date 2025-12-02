package searchengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a single web page with a URL, title, and searchable words.
 * Once created, the page data cannot be modified.
 * Used by the page collection and search engine.
 */
public class Page {
    private static final int URL_LINE_INDEX = 0;
    private static final int URL_FIRST_LETTER_INDEX = 6;
    private static final int TITLE_LINE_INDEX = 1;
    private static final int CONTENT_START_INDEX = 2;

    private final String url;
    private final String title;
    private final List<String> words;

    /**
     * Creates a new page from raw page data.
     *
     * @param rawPage list of strings where first line is URL, second is title, and rest is content
     */
    public Page(List<String> rawPage) {
        url = rawPage.get(URL_LINE_INDEX).substring(URL_FIRST_LETTER_INDEX);
        title = rawPage.get(TITLE_LINE_INDEX);

        words = new ArrayList<>();
        for (int i = CONTENT_START_INDEX; i < rawPage.size(); i++) {
            String currentLine = rawPage.get(i);
            String[] wordsCurrentLine = currentLine.split("\\s+");
            words.addAll(Arrays.asList(wordsCurrentLine));
        }
    }

    /**
     * @return the page URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the page title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return an unmodifiable list of all words in the page content
     */
    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

}
