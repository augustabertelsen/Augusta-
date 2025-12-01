package searchengine;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageCollectionTest {

    @Test
    void testInvalidPagesAreFiltered() throws IOException {
        // test-file-errors.txt in the data folder has two valid pages.
        PageCollection testPageCollection = new PageCollection("data/test-file-errors.txt");
        assertEquals(2, testPageCollection.getPages().size());
    }
}
