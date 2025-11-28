package searchengine;

import java.util.ArrayList;
import java.util.List;

public class QueryHandler {


    public QueryHandler() {

    }

    public List handleQuery(List<String> searchTerm) {
        List<List<String>> termList = new ArrayList<List<String>>();
        List<String> searchT = new ArrayList();
        searchT = searchTerm;
        List<String> list1 = new ArrayList<String>();
        termList.add(list1);
        List currentList = list1;

        for (String word : searchT) {
            if (!word.equals("OR")) {
                currentList.add(cleanSearchTerm(word));

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
