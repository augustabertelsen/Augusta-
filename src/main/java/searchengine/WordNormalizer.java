
/**
 * Cleans a single search term so it can be used consistently.
 * Converts the input to lowercase, trims whitespace, and removes trailing
 * punctuation, ensuring the same word is represented uniformly across user
 * queries and page contents. Returns a cleaned string or an empty string if
 * the input is null or becomes empty.
 *
 * Used by: QueryHandler and InvertedIndex.
 */

package searchengine;

public class WordNormalizer {
    
// private empty constructor because it prevents anyone from creating an instance of 
// a "utility class" that only provides static methods.
private WordNormalizer(){
}


// public static so any class can use the normalization logic without creating an object, 
// since the method belongs to the utility itself rather than an instance.
// -> String normalized = WordNormalizer.normalize(word);

public static String normalize(String searchTerm){
    if(searchTerm == null){
        return "";
    }
    String cleanSearchTerm = searchTerm.toLowerCase().trim();
    if (cleanSearchTerm.isEmpty()) {
            return cleanSearchTerm;
        }
    int wordLength = cleanSearchTerm.length();
        char lastCharacter = cleanSearchTerm.charAt(wordLength - 1);

        if (!Character.isLetter(lastCharacter)) {
            return cleanSearchTerm.substring(0, wordLength - 1);
        } else {
            return cleanSearchTerm;
        }
    
}



}
