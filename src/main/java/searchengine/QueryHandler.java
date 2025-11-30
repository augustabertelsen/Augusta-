package searchengine;

import java.util.ArrayList;
import java.util.List;

public class QueryHandler {

    List<List<String>> termList;

    public QueryHandler() {

        termList = new ArrayList<List<String>>();
    }

    public List handleQuery(List<String> searchTerm) {

        List<String> list1 = new ArrayList<String>();
        termList.add(list1);

        currentList = list1;

        for (String word : searchTerm) {
            if (!word.equals("OR")) {
                currentList.add(cleanSearchTerm(word));

            }

            else {
                List<String> list = new ArrayList<String>();
                termList.add(list);
                currentList = list;
            }

        }
        return termlist;
    }


// use WordNormalizer instead -> String normalized = WordNormalizer.normalize(token);
    
    // private String cleanSearchTerm(String searchTerm) {

    //     String cleanSearchTerm = searchTerm.toLowerCase().trim();

    //     if (cleanSearchTerm.isEmpty()) {
    //         return cleanSearchTerm;
    //     }

    //     int wordLength = cleanSearchTerm.length();
    //     char lastCharacter = cleanSearchTerm.charAt(wordLength - 1);

    //     if (!Character.isLetter(lastCharacter)) {
    //         return cleanSearchTerm.substring(0, wordLength - 1);
    //     } else {
    //         return cleanSearchTerm;
    //     }
    // }

}
