// Loads all pages from the data file, splits the file into separate page blocks,
// parses each into a Page object, and stores them. 
// The only class that "understands" the structure of the source file.
  // Reads the file -> Split it into individual pages -> Create Page objects and store them.

// Handles file loading and page creation, while the index only handles fast word to page lookup.
// Avoids mixing data loading with search logic.


package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageCollection{
    private List<Page> pages;
    private String filename;    //might not be necessary? 

// when writing: 
    // new PageCollection("datafile.txt")

// The constructor loads every line of the file, starts at bottom, goes backward to find each `*PAGE` marker, 
// and slices out the lines that belong to that page.
        // pageView: pageView is a subList view, not a real independent list -> shows which lines belong to the current page. (Safer)
        // pageCopy: new independent list -> copies lines in pageView so each Page object 
                // NOTE: maybe both are not needed?
// Each pageView (/pageCopy) becomes a Page object, and all Pages are stored in pages List then reversed to restore the correct order.

public PageCollection(String filename) throws IOException{
  this.filename = filename;
  
  this.pages = new ArrayList<>();
      List<String> lines = Files.readAllLines(Paths.get(filename));
      var lastIndex = lines.size();
      for (var i = lines.size() - 1; i >= 0; --i) {
        if (lines.get(i).startsWith("*PAGE")) {
          List<String> pageView = lines.subList(i, lastIndex);  //subList view: a window that points to the original list -> not a new independent list 
          List<String> pageCopy = new ArrayList<>();
             for(String line : pageView){
                pageCopy.add(line);
              }
          Page page = new Page(pageCopy);  
          pages.add(page);
          lastIndex = i;
        }
      }
    Collections.reverse(pages);
}

// getters
    // Other classes can read the list and iterate over the pages, 
    // they cannot change it -> no adding, removing, or modifying the list. 
    public List<Page> getPages() {
      List<Page> wrappedPages = Collections.unmodifiableList(pages);
      return wrappedPages;
    }

    public String getFilename() {   //might not be necessary? 
        return filename;
    }

}



// Code from OldWebServer: 

  //  // raw pages storage
  //   List<List<String>> pages = new ArrayList<>();


  //   // read file + split into pages
  //   try {
  //     List<String> lines = Files.readAllLines(Paths.get(filename));
  //     var lastIndex = lines.size();
  //     for (var i = lines.size() - 1; i >= 0; --i) {
  //       if (lines.get(i).startsWith("*PAGE")) {
  //         pages.add(lines.subList(i, lastIndex));
  //         lastIndex = i;
  //       }
  //     }
  //   } catch (FileNotFoundException e) {
  //     e.printStackTrace();
  //   }

  //   //ordering of pages 
  //   Collections.reverse(pages);
