package searchengine;

import java.beans.Transient;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import searchengine.InvertedIndex;
import searchengine.Page;

class QueryHandlerTest {

    /**
 * Verifies that the cleanSearchTerm() method removes all 
 * punctuation and trailing spaces from a search term and 
 * converts it to lowercase.
 */

    @Test 
    void cleansCapitalisedWordWithExclamationMarkAndTrailingSpace(){
        String testWord = "Kapow! ";

        QueryHandler handler = new QueryHandler();

        assertTrue(handler.cleanSearchTerm(testWord).equals("kapow"));
    }
    
    /**
* Verifies that the cleanSearchTerm() method , when called upon a word 
* which already has no capitalisation, punctuation or trailing spaces
* returns the word as given. 
*/

    @Test
    void leavesAlreadyCleanWordAsItIs() {
        String testWord = "clean";

        QueryHandler handler = new QueryHandler();

        assertTrue(handler.cleanSearchTerm(testWord).equals("clean"));
    }
 /**
 * Verifies that the cleanSearchTerm() method, when called on an empty 
 * string, returns an empty string. 
 */
    
    @Test 
    void returnsEmptyStringWhenCalledOnEmptyString() {
        String testWord = "";

        QueryHandler handler = new QueryHandler();

        assertTrue(handler.cleanSearchTerm(testWord).equals(""));
    }

/**
 * Verifies that, when the handleQuery() method is called on a list of two Strings
 * neither of which is "OR", the method returns a List which contains one List<String>
 * which contains the two initial strings. 
 */
 @Test 
    void returnsTwoNonOrStrings() {
        List<String> testList = new ArrayList<String>();
        testList.add("dog");
        testList.add("house");

        QueryHandler handler = new QueryHandler();

        assertTrue(handler.handlerQuery(testList).size() == 1);
        assertTrue(handler.handleQuery(testList)(0)(0).equals("dog"));
        assertTrue(handler.handleQuery(testList)(0)(1).equals("house"));
    }

    /**
 * Verifies that, when the handleQuery() method is called on a list of three Strings
 * the second of which is "OR", the method returns a List of two Lists: one which contains 
 * the first string and another which contains the second. 
 */
 @Test 
    void makesANewListWhenEncountersOR() {
        List<String> testList = new ArrayList<String>();
        testList.add("dog");
        termList.add("OR");
        testList.add("house");

        QueryHandler handler = new QueryHandler();

        assertTrue(handler.handlerQuery(testList).size() == 2);
        assertTrue(handler.handleQuery(testList)(0)(0).equals("dog"));
        assertTrue(handler.handleQuery(testList)(1)(0).equals("house"));
    }


}
