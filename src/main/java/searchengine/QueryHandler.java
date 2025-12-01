package searchengine;

import java.util.ArrayList;
import java.util.List;

public class QueryHandler {


    public QueryHandler() {

    }

    public List<List<String>> handleQuery(List<String> searchTerm) {
        List<List<String>> termList = new ArrayList<List<String>>();
        List<String> list1 = new ArrayList<String>();
        termList.add(list1);
        List<String> currentList = list1;

        for (int i = 0; i < searchTerm.size(); i++) {
            String currentWord = searchTerm.get(i);

            if (!currentWord.equals("OR")) {
                currentList.add(cleanSearchTerm(currentWord));

            }

            else {
                List<String> list = new ArrayList<String>();
                termList.add(list);
                currentList = list;
            }

        }
        return termList;
    }

    private String cleanSearchTerm(String searchTerm) {

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
