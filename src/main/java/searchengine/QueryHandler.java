// package searchengine;

// import java.util.List;

// public class QueryHandler {


//     public QueryHandler(List<String> searchTerm) { }
    

        
//     List<List<String>> termList = new List<List<String>>();

//         List<String> list1 = new List<String>();
//         List<String> list2 = new List<String>();
//         termList.add(list1);
//         termList.add(list2);

//         currentList = list1;

//         for (String word: searchTerm) {
//             if (!word.equals("OR")) {
//                 currentList.add(cleanSearchTerm(word));
//             }

//             else {
//                 currentList = list2;
//             }

//         }

//         return termlist;
// }

// private String cleanSearchTerm(String searchTerm) {
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
//         }} 

