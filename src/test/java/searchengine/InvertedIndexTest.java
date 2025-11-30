package searchengine;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class InvertedIndexTest {

    @Test
    void lookUpReturnsPagesForNormalizedWord() {
        Page pageA = new Page(List.of(
            "*PAGE:http://example.com/A",
            "Some title",
            "A, President, USA"
        ));
        Page pageB = new Page(List.of(
            "*PAGE:http://example.com/B",
            "Some title",
            "king", "denmark"
        ));
        List<Page> allPages = Arrays.asList(pageA, pageB);

        InvertedIndex index = new InvertedIndex(allPages);

        List<Page> result = index.lookUp("PRESIDENT!");

        assertTrue(result.contains(pageA));
        assertFalse(result.contains(pageB));
        assertEquals(1, result.size());
    }
    
}
