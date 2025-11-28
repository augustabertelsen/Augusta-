package searchengine;

import java.util.ArrayList;
import java.util.List;

public class QueryHandler {

     List<List<String>> termList;
    

    public QueryHandler(List<String> searchTerm) {
   
      termList = new List<List<String>>();  
    }

    public List handleQuery(){
        List<String> list1 = new List<String>();
        List<String> list2 = new List<String>();
        termList.add(list1);
        termList.add(list2);

        currentList = list1;

        for (String word: searchTerm) {
            if (!word.equals("OR")) {
                currentList.add(cleanSearchTerm(word));
        
            }

            else {
                currentList = list2;
            }

        }
        return termlist;
    }

   
    private String cleanSearchTerm(String searchTerm) {
        // CHANGED: Same behavior as before – lower case and trim whitespace.
        String cleanSearchTerm = searchTerm.toLowerCase().trim();

        // CHANGED: If the cleaned term is now empty, just return it to avoid charAt errors.
        if (cleanSearchTerm.isEmpty()) {
            return cleanSearchTerm;
        }

        // CHANGED: Compute length and last character.
        int wordLength = cleanSearchTerm.length();
        char lastCharacter = cleanSearchTerm.charAt(wordLength - 1);

        // CHANGED: If last char is not a letter, strip it off; otherwise return as-is.
        if (!Character.isLetter(lastCharacter)) {
            return cleanSearchTerm.substring(0, wordLength - 1);
        } else {
            return cleanSearchTerm;
        }
    }

    
}
