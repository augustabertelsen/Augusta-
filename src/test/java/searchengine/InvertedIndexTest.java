package searchengine;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class InvertedIndexTest {

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
            "fun", "laugh", "smile"
        ));

        List<Page> allPages = Arrays.asList(pageA, pageB, pageC);
        InvertedIndex index = new InvertedIndex(allPages);

        List<Page> result = index.lookUp("laugh");

        assertTrue(result.contains(pageA));
        assertTrue(result.contains(pageC));
        assertFalse(result.contains(pageB));
        assertEquals(2, result.size());
    }






}
