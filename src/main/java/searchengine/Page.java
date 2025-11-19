// Represents a single page with a URL, title, and list of words. 
// Data model that cannot be changed after created
// Used by the page collection + search engine.

package searchengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Page {
    private String url;
    private String title;
    private List<String> words;

    public Page(List<String> rawPage) {
        // rawPage is a list of Strings that looks like this:
        // "*PAGE http..."      -> index 0
        // "Title"              -> index 1
        // "word1 word2", ...   -> index 2 til end 

        // url: get String at index 0 of the list : *PAGE http... + start url substring af index 6 of the String at index 0: http...
        this.url = rawPage.get(0).substring(6);
        // title: get String at index 1 of the List of Strings in rawPage
        this.title = rawPage.get(1);

        // words: initialize word list, loop from index 2 of rawPage til end, split each line into substrings, add substrings to words list
        this.words = new ArrayList<>();
        for (int i = 2; i < rawPage.size(); i++) {
            String currentLine = rawPage.get(i);
            String[] wordsCurrentLine = currentLine.split("\\s+");
            words.addAll(Arrays.asList(wordsCurrentLine));
        }
    }

    //getters:
    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getWords() {
        return words;
    }

}
