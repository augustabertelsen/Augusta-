package searchengine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.Transient;

import org.junit.jupiter.api.Test;

public class WordNormalizerTest {

       /**
 * Verifies that the normalize() method removes all 
 * punctuation and trailing spaces from a search term and 
 * converts it to lowercase.
 */

    @Test 
    void cleansCapitalisedWordWithExclamationMarkAndTrailingSpace(){
        String testWord = "Kapow! ";

        assertTrue(WordNormalizer.normalize(testWord).equals("kapow"));
    }
    
    /**
* Verifies that the normalize() method , when called upon a word 
* which already has no capitalisation, punctuation or trailing spaces
* returns the word as given. 
*/

    @Test
    void leavesAlreadyCleanWordAsItIs() {
        String testWord = "clean";


        assertTrue(WordNormalizer.normalize(testWord).equals("clean"));
    }
 /**
 * Verifies that the normalize() method, when called on an empty 
 * string, returns an empty string. 
 */
    
    @Test 
    void returnsEmptyStringWhenCalledOnEmptyString() {
        String testWord = "";

        assertTrue(WordNormalizer.normalize(testWord).equals(""));
    }

    /**
     * Verifies that a space (" ") returns an empty string.
     */

    @Test
    void returnsEmptyStringWhenCalledOnSpace() {
        String testWord = " ";

        assertTrue(WordNormalizer.normalize(testWord).equals(""));
    }

    /** 
     * Verifies that a single punctuation mark returns an empty String.
     */

    @Test
    void returnsEmptyStringWhenCalledOnPunctuationOnly() {
        String testWord = "?";
        assertTrue(WordNormalizer.normalize(testWord).equals(""));
    }
}
