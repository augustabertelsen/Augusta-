package searchengine;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class InvertedIndexTest {

    @Test 
    void lookUpSingleWordSinglePage(){
        Page pageA = new Page(List.of(
            "*PAGE:http://example.com/A",
            "Title A",
            "government, president"
        ));

        List<Page> allPages = Arrays.asList(pageA);
        InvertedIndex index = new InvertedIndex(allPages);

        List<Page> result = index.lookUp("government");

        assertTrue(result.size() == 1);
        assertTrue(result.contains(pageA));
    }
    
    @Test 
    void lookUpWordInMultiplePages(){
        Page pageA = new Page(List.of(
            "*PAGE:http://example.com/A",
            "Title A",
            "dog", "president", "laugh"
        ));
        Page pageB = new Page(List.of(
            "*PAGE:http://example.com/B",
            "Title B",
            "king", "denmark", "dog"
        ));
        Page pageC = new Page(List.of(
            "*PAGE:http://example.com/C",
            "Title C",
            "fun", "laugh"
        ));

        List<Page> allPages = Arrays.asList(pageA, pageB, pageC);
        InvertedIndex index = new InvertedIndex(allPages);

        List<Page> result = index.lookUp("laugh");

        assertTrue(result.contains(pageA));
        assertTrue(result.contains(pageC));
        assertFalse(result.contains(pageB));
        assertEquals(2, result.size());
    }

    @Test
    void lookUpNormalizedWord(){
        Page pageA = new Page(List.of(
            "*PAGE:http://example.com/A",
            "Title A",
            "Government, President, USA"
        ));

        List<Page> allPages = Arrays.asList(pageA);
        InvertedIndex index = new InvertedIndex(allPages);

        List<Page> result1 = index.lookUp("PRESIDENT!");
        List<Page> result2 = index.lookUp("president");
        List<Page> result3 = index.lookUp("Predsident.");

        assertTrue(result1.size() == 1 && result1.contains(pageA));
        assertTrue(result2.size() == 1 && result1.contains(pageA));
        assertTrue(result3.size() == 1 && result1.contains(pageA));
    }

    @Test 
    void lookUpMultipleWords(){
        Page pageA = new Page(List.of(
            "*PAGE:http://example.com/A",
            "Title A",
            "walking the dog", "the president is in the goverment", "laughing a lot"
        ));

        List<Page> allPages = Arrays.asList(pageA);
        InvertedIndex index = new InvertedIndex(allPages);

        List<Page> result = index.lookUp("the president is in the goverment");

        assertTrue(result.contains(pageA));
        assertEquals(1, result.size());
    }

    @Test 
    void lookUpReturnEmptyPageForUnknownWord(){
        Page pageA = new Page(List.of(
            "*PAGE:http://example.com/A",
            "Title A",
            "government"
        ));

        List<Page> allPages = Arrays.asList(pageA);
        InvertedIndex index = new InvertedIndex(allPages);

        List<Page> result = index.lookUp("laugh");

        assertNotEquals(null, result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    void lookUpReturnsOnePageForRepeatedWord(){
        Page pageA = new Page(List.of(
            "*PAGE:http://example.com/A",
            "Title A",
            "government", "government", "president"
        ));

        List<Page> allPages = Arrays.asList(pageA);
        InvertedIndex index = new InvertedIndex(allPages);

        List<Page> result = index.lookUp("government");

        assertTrue(result.contains(pageA));
        assertEquals(1, result.size());
    }



}
