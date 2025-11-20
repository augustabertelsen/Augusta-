// Represents a single page with a URL, title, and list of words. 
// Data model that cannot be changed after created
// Used by the page collection + search engine.

package searchengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Page {
    private static final int URL_LINE_INDEX = 0;
    private static final int URL_FIRST_LETTER_INDEX = 6;
    private static final int TITLE_LINE_INDEX = 1;
    private static final int CONTENT_START_INDEX = 2;
    
    private final String url;
    private final String title;
    private final List<String> words;

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

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words); // make sure to return unmodifiable list so returned list cant be modified by other classes
    }

}
