
// Shared helper for cleaning one String before it is used as a search word. It takes one String searchTerm and “cleans” it.
// Makes it lowercase, removes extra spaces, and cuts off a final punctuation, so the same word is written in the same way
// everywhere, no matter how the user typed it or how it appears in the file.
// Input: String searchTerm.
// Output: Cleaned String or empty String
// Used by: QueryHandle and InvertedIndex

package searchengine;

public class WordNormalizer {
    
// private so no one can create a object 
private WordNormalizer(){
}


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
