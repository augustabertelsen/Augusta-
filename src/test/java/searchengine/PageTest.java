package searchengine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class PageTest {

    @Test
    void testPageCreation() {
        String testUrl = "http://example.com";
        String testTitle = "Example Title";
        String testLine1 = "hello world";
        String testLine2 = "cool test";
        List<String> allWordsInTestLines = List.of((testLine1 + " " + testLine2).split(" "));

        List<String> rawPage = Arrays.asList(
            "*PAGE:"+testUrl, // *PAGE:http://example.com
            testTitle,
            testLine1,
            testLine2
        );

        Page page = new Page(rawPage);
        
        assertEquals(testUrl, page.getUrl());
        assertEquals(testTitle, page.getTitle());
        assertEquals(allWordsInTestLines, page.getWords());
    }
}