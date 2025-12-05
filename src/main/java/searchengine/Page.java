package searchengine;

import java.util.ArrayList;
import java.util.Arrays;
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
    private double rankingScore = 0;

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
     * Assigns a ranking score to the Page if the provided score is greater than the
     * Page's current ranking score.
     *
     * @param score the new ranking score to be assigned to the Page
     */
    public void assignRankingScoreIfGreaterThanCurrent(double score) {
        if (this.rankingScore < score) {
            this.rankingScore = score;
        }
    }

    /**
     * @return the ranking score of the Page.
     */
    public double getRankingScore() {
        return rankingScore;
    }

    /**
     * Sets the ranking score of the page to 0.
     */
    public void clearRankingScore() {
        rankingScore = 0;
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
     * @return an unmodifiable list of all words in page content.
     */
    public List<String> getWords() {
        return List.copyOf(words);
    }
    /**
     * @return an unmodifiable list of all words from both title and page content.
     */
    public List<String> getTotalWords() {
        List<String> totalWords = new ArrayList<>(Arrays.asList(title.split("\\s+")));
        totalWords.addAll(words);
        return List.copyOf(totalWords);
    }
}
