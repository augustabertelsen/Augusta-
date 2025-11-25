import java.util.ArrayList;
import java.util.List;

import searchengine.InvertedIndex;
import searchengine.Page;

public class SearchQueryEngineHandler {

    private InvertedIndex index;
    
    public SearchQueryEngineHandler(InvertedIndex index) {
        this.index = index;
    }
    
    public byte[] handleSearch(HttpExchange io) {
        List<String> searchTerm = io.getRequestURI().getRawQuery().split("=");
        List<Page> termPages = new List<Pages>();
        List<List<String>> termList = new List<List<String>>();

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
}

private String cleanSearchTerm(String searchTerm) {
        // change upper case letters to lowee case and removes whitespace from bothe ends of string 
        String cleanSearchTerm = searchTerm.toLowerCase().trim();
        // variable with lengt of searchword
        int wordLength = cleanSearchTerm.length();
        // variable containing last character of word
        char lastCharacter = cleanSearchTerm.charAt(wordLength-1);

        // if the last character is not a letter return cleanSeachTerme minus last character else return cleanSeachTerme
        if(!Character.isLetter(lastCharacter)){
            return cleanSearchTerm.substring(0,wordLength-1);
        } else { 
            return cleanSearchTerm;
        }}

        //search index for pages containing all terms in term list and return as termPages

         List<String> jsonStrings = new ArrayList<>();

        // Each page must become a small JSON object before they can be combined
        // A JSON object string is just text that follows JSON rules.
        // Goal is for it to look like: {"url": "...", "title": "..."}
        for (Page page : termPages) {
            String url = page.getUrl();
            String title = page.getTitle();
            String json = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);

            //temporary storage before structure
            jsonStrings.add(json);
        }

        //Now we create one JSON array Stringthat is correctly structured
        String result = "[ ";
        for (int i = 0; i <= jsonStrings.size() - 1; i++) {
            result += jsonStrings.get(i);

            if (i < jsonStrings.size() - 1) {
                result += ", ";
            }
        }

        result += " ]";

        // convert String to byte
        // return byte
        result = result.getBytes();
        return result.getBytes();

    }
