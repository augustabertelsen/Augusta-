
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

 

// REMOVED (commented) ORIGINAL INDEX-BASED IMPLEMENTATION:
    // public List<Page> search(String searchTerm) {
    //     String cleanSearchTerm = cleanSearchTerm(searchTerm);
    //     return index.lookUp(cleanSearchTerm);
    // }
    //
    // REMOVED: `private InvertedIndex index;` field and all javadoc that said
    // "Uses the InvertedIndex to find pages" because we now search via pages list only.




// OldSearchEngine

// public class OldSearchEngine {
//     // uses to find pages fast
//     private InvertedIndex index;

//     // // provides access to all pages and their raw data (maybe remove)
//     // private PageCollection collection;

//     // Constructor builds the search engine -> DOES NOT perform search
//     // Needs InvertedIndex to perfom lookups
//     public OldSearchEngine(InvertedIndex index) {
//         this.index = index;
//     }

//     public List<Page> search(String searchTerm) {
//         //uses helpermethod cleanSeachTerm() that return the original searchTerm with out uppercase, whitespace and punctuation 
//         String cleanSearchTerm = cleanSearchTerm(searchTerm);
//         return index.lookUp(cleanSearchTerm); 
//     }

//         private String cleanSearchTerm(String searchTerm) {
//         // change upper case letters to lowee case and removes whitespace from bothe ends of string 
//         String cleanSearchTerm = searchTerm.toLowerCase().trim();
//         // variable with lengt of searchword
//         int wordLength = cleanSearchTerm.length();
//         // variable containing last character of word
//         char lastCharacter = cleanSearchTerm.charAt(wordLength-1);

//         // if the last character is not a letter return cleanSeachTerme minus last character else return cleanSeachTerme
//         if(!Character.isLetter(lastCharacter)){
//             return cleanSearchTerm.substring(0,wordLength-1);
//         } else { 
//             return cleanSearchTerm;
//         }
//     }

// }

// Code from OldWebServer
// // search algorithm:
// List<List<String>> search(String searchTerm) {
// var result = new ArrayList<List<String>>();
// for (var page : pages) {
// if (page.contains(searchTerm)) {
// result.add(page);
// }
// }
// return result;
// }
    

}
