package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is responsible for loading all pages from a data file, splitting the file 
 * into separate page blocks, parsing each into a Page object, and storing them.
 */
public class PageCollection {
    private final List<Page> pages;

    /**
     * Creates a new PageCollection by loading and parsing pages from the specified file.
     * <p>
     * The file is expected to contain page blocks, where each page starts with a line 
     * beginning with "*PAGE". The constructor reads the entire file, identifies page 
     * boundaries, and creates Page objects for each block.
     * @param filePath the path to the data file containing page information.
     * @throws IOException if the file cannot be read or does not exist.
     */
    public PageCollection(String filePath) throws IOException {
        pages = new ArrayList<>();
        List<String> allLinesFromFile = Files.readAllLines(Paths.get(filePath));
        var lastIndex = allLinesFromFile.size();
        for (var i = allLinesFromFile.size() - 1; i >= 0; --i) {
            if (allLinesFromFile.get(i).startsWith("*PAGE")) {
                List<String> pageLinesView = allLinesFromFile.subList(i, lastIndex);  //subList view: a window that points to the original list
                List<String> pageLines = new ArrayList<>(pageLinesView); // create a new independent list containing only the page
                if (isValidPage(pageLines)) {
                    Page page = new Page(pageLines);
                    pages.add(page);
                }
                lastIndex = i;
            }
        }
        Collections.reverse(pages);
    }

    /**
     * @return an unmodifiable list containing all pages in this collection.
     */
    public List<Page> getPages() {
        return Collections.unmodifiableList(pages);
    }

    /**
     * Checks if a raw page contains both a title and at least one word of content.
     * @param pageLines list of Strings representing the lines of the page (*PAGE line, title and word lines)
     * @return true if the page has a title (line 1) and at least one word (line 2), false otherwise
     */
    private boolean isValidPage(List<String> pageLines) {
        // Must have at least a *PAGE line, title line and one word (three lines total)
        return pageLines.size() >= 3;
    }
}