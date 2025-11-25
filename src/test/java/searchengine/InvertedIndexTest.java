package searchengine;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class InvertedIndexTest {
    

// Tests the observable behavior of InvertedIndex via lookUp, 
// which indirectly verifies that the constructor built the internal map correctly.
@Test
void testOnePage() {
    // Arrange: build raw page lines in the format Page expects
    List<String> rawPage = new ArrayList<>();
    rawPage.add("url:http://example.com");   // index 0
    rawPage.add("My title");                 // index 1
    rawPage.add("cat dog horse");            // index 2+

    Page page = new Page(rawPage);

    List<Page> allPages = List.of(page);
    InvertedIndex index = new InvertedIndex(allPages);

    // Act
    List<Page> catPages = index.lookUp("cat");
    List<Page> dogPages = index.lookUp("dog");
    List<Page> missingPages = index.lookUp("mouse");

    // Assert
    assertTrue(catPages.contains(page));
    assertTrue(dogPages.contains(page));
    assertTrue(missingPages.isEmpty());
}




}
