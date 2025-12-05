package searchengine;

/**
 * Cleans a single search term so it can be used consistently.
 * Converts the input to lowercase, trims whitespace, and removes trailing
 * punctuation, ensuring the same word is represented uniformly across user
 * queries and page contents. Returns a cleaned string or an empty string if
 * the input is null or becomes empty.
 * <p>
 * Used by: QueryHandler and InvertedIndex.
 */
public class WordNormalizer {

    /**
     * Normalizes a search term by converting to lowercase, trimming whitespace, and removing trailing punctuation.
     *
     * @param searchTerm the search term to normalize
     * @return the normalized search term, or an empty string if input is null or becomes empty
     */
    public static String normalize(String searchTerm) {
        if (searchTerm == null) {
            return "";
        }
        String cleanSearchTerm = searchTerm.toLowerCase().trim();
        if (cleanSearchTerm.isEmpty()) {
            return cleanSearchTerm;
        }
        int wordLength = cleanSearchTerm.length();
        char lastCharacter = cleanSearchTerm.charAt(wordLength - 1);

        if (!Character.isLetterOrDigit(lastCharacter)) {
            return cleanSearchTerm.substring(0, wordLength - 1);
        } else {
            return cleanSearchTerm;
        }
    }
}
