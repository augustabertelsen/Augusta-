
/**
 * Cleans a single search term so it can be used consistently.
 * Converts the input to lowercase, trims whitespace, and removes trailing
 * punctuation, ensuring the same word is represented uniformly across user
 * queries and page contents. Returns a cleaned string or an empty string if
 * the input is null or becomes empty.
 * <p>
 * Used by: QueryHandler and InvertedIndex.
 */

package searchengine;

public class WordNormalizer {

   
// all methods are static -> class does not need a constructor 
    // WordNormalizer() {
    // }


// Declared static, it belongs to the class itself, not to any particular object.
// Static methods can be called as WordNormalizer.normalize(...) without creating a new WordNormalizer().
// -> String normalized = WordNormalizer.normalize(word);

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
