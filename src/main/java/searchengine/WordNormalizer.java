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
