package searchengine;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class QueryHandlerTest {

 
/**
 * Verifies that, when the handleQuery() method is called on a list of two Strings
 * neither of which is "OR", the method returns a List which contains one List<String>
 * which contains the two initial strings. 
 */
 @Test 
    void returnsTwoNonOrStrings() {
        List<String> testList = new ArrayList<>();
        testList.add("dog");
        testList.add("house");

        QueryHandler handler = new QueryHandler();

        assertTrue(handler.handleQuery(testList).size() == 1);
        assertTrue(handler.handleQuery(testList).get(0).size() == 2);
        assertTrue(handler.handleQuery(testList).get(0).get(0).equals("dog"));
        assertTrue(handler.handleQuery(testList).get(0).get(1).equals("house"));
    }

    /**
 * Verifies that, when the handleQuery() method is called on a list of three Strings
 * the second of which is "OR", the method returns a List of two Lists: one which contains 
 * the first string and another which contains the second. 
 */
 @Test 
    void makesANewListWhenEncountersOR() {
        List<String> testList = new ArrayList<>();
        testList.add("dog");
        testList.add("OR");
        testList.add("house");

        QueryHandler handler = new QueryHandler();

        assertTrue(handler.handleQuery(testList).size() == 2);
        assertTrue(handler.handleQuery(testList).get(0).size() == 1);
        assertTrue(handler.handleQuery(testList).get(1).size() == 1);
        assertTrue(handler.handleQuery(testList).get(0).get(0).equals("dog"));
        assertTrue(handler.handleQuery(testList).get(1).get(0).equals("house"));
    }

    /**
 * Verifies that, when the handleQuery() method is called on a list containing a string which has capital letters, 
 * punctuation marks or a trailing space, that these are removed before the method adds the string to a new list. 
 */
 @Test 
    void returnsCleanString() {
        List<String> testList = new ArrayList<>();
        testList.add("CAT! ");

        QueryHandler handler = new QueryHandler();

        assertTrue(handler.handleQuery(testList).size() == 1);
        assertTrue(handler.handleQuery(testList).get(0).size() == 1);
        assertTrue(handler.handleQuery(testList).get(0).get(0).equals("cat"));
    }

    /**
 * Verifies that, when the handleQuery() method is called on a list of more than two strings divided by a single "OR", 
 * the method returns a list containing firstly a list of all the strings which appear before the "OR" and 
 * secondly a list of all the strings which appear after the "OR". 
 */

@Test
    void returnsListOfListsOfMultipleStrings() {
        List<String> testList = new ArrayList<>();
        testList.add("dog");
        testList.add("house");
        testList.add("OR");
        testList.add("kennel");

        QueryHandler handler = new QueryHandler();

        assertTrue(handler.handleQuery(testList).size() == 2);
        assertTrue(handler.handleQuery(testList).get(0).size() == 2);
        assertTrue(handler.handleQuery(testList).get(1).size() == 1);
        assertTrue(handler.handleQuery(testList).get(0).get(0).equals("dog"));
        assertTrue(handler.handleQuery(testList).get(0).get(1).equals("house"));
        assertTrue(handler.handleQuery(testList).get(1).get(0).equals("kennel"));
    }

     /**
 * Verifies that, when the handleQuery() method is called on a list of strings divided by multiple "OR" terms, 
 * the method returns multiple lists which include all the terms in the parameterised list, divided into lists 
 * depending on where the "OR" terms occur. 
 */
    @Test
    void returnsListOfThreeListsOfSingleStrings() {
        List<String> testList = new ArrayList<>();
        testList.add("dog");
        testList.add("OR");
        testList.add("cat");
        testList.add("OR");
        testList.add("mouse");

        QueryHandler handler = new QueryHandler();

        assertTrue(handler.handleQuery(testList).size() == 3);
        assertTrue(handler.handleQuery(testList).get(0).size() == 1);
        assertTrue(handler.handleQuery(testList).get(1).size() == 1);
        assertTrue(handler.handleQuery(testList).get(2).size() == 1);
        assertTrue(handler.handleQuery(testList).get(0).get(0).equals("dog"));
        assertTrue(handler.handleQuery(testList).get(1).get(0).equals("cat"));
        assertTrue(handler.handleQuery(testList).get(2).get(0).equals("mouse"));
    }


}
