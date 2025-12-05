package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for receiving a search term as input as a List<String>, 
 * 'cleaning' each String to convert capital letters to lowercase and removing 
 * punctuation and trailing spaces, adding each string to a list, creating a new 
 * list if it comes across a string that is equal to "OR", and returning a List<List<String>>.
 */

public class QueryHandler {

    /**
     * Creates a new QueryHandler. No parameters are required to instantiate a new 
     * object of QueryHandler. 
     */

    public QueryHandler() {

    }
    /**
     * Creates a new List<String>, which is added to a List<List<String>>. Each String in the paramaterised
     * List<String> is passed through the 'cleanSearchTerm()' method and added. However, if that word is "OR", 
     * a new List<String> is created and added to the List<List<String>>. All subsequent words are added to 
     * this new List<String>, until there are no more words to examine or another "OR" is hit, in which case 
     * a new List<String> is made as before. 
     * 
     * @param searchTerm a List<String> which is created by the SearchEngine class. 
     * @return a List<List<String>> comprised of a list of lists including every non-OR String in the paramaterised searchTerm list, divided
     * into lists according to where the method found the "OR" terms. 
     */

    public List<List<String>> handleQuery(List<String> searchTerm) {
        List<List<String>> termList = new ArrayList<>();
        List<String> orGroupList = new ArrayList<>();
        termList.add(orGroupList);
        List<String> currentList = orGroupList;

        for (String currentWord : searchTerm) {
            if (!currentWord.equals("OR")) {
                currentList.add(WordNormalizer.normalize(currentWord));
            } else {
                List<String> listORGroup = new ArrayList<>();
                termList.add(listORGroup);
                currentList = listORGroup;
            }

        }
        return termList;
    }

}
