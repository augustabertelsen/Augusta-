package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for processing search queries by splitting them into OR groups.
 * It receives a search query as a List<String>, normalizes each term using WordNormalizer,
 * and organizes the terms into separate lists based on "OR" delimiters. Returns a
 * List<List<String>> where each inner list represents terms that should be ANDed together,
 * and the outer list represents OR groups.
 */

public class QueryHandler {

    /**
     * Processes a search query by splitting it into OR groups. Each
     * String in the parameterised List<String> is normalized using WordNormalizer.normalize() and added
     * to the current group. However, if that word is "OR", a new List<String> is created and added to the
     * List<List<String>>. All subsequent words are added to this new List<String>, until there are no more
     * words to examine or another "OR" is encountered, in which case a new List<String> is created as before.
     * Only normalized words that are non-empty and start with a letter or digit are included.
     * @param searchTerm a List<String> which is created by the SearchEngine class.
     * @return a List<List<String>> comprised of lists of normalized search terms, with each inner list
     * representing an OR group, divided according to where "OR" words were found.
     */

    public List<List<String>> handleQuery(List<String> searchTerm) {
        List<List<String>> termList = new ArrayList<>();
        List<String> orGroupList = new ArrayList<>();
        termList.add(orGroupList);
        List<String> currentList = orGroupList;

        for (String currentWord : searchTerm) {
            if (!currentWord.equals("OR")) {
                String normalizedWord = WordNormalizer.normalize(currentWord);
                if (!normalizedWord.isEmpty() && Character.isLetterOrDigit(normalizedWord.charAt(0))) {
                    currentList.add(normalizedWord);
                }
            } else {
                List<String> listORGroup = new ArrayList<>();
                termList.add(listORGroup);
                currentList = listORGroup;
            }

        }
        return termList;
    }

}
