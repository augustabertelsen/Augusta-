// Starts the application. Reads config, creates all components, wires them together, and launches the WebServer.


package searchengine;

import java.util.List;

public class Main {
    public static void main(String[] args) {
// Logic that belongs in Main:
        // Load all pages -> using PageCollection
        PageCollection collection = new PageCollection("datafile.txt");
        List<Page> allPages = collection.getPages();

        // Build the inverted index -> using all the pages
        InvertedIndex index = new InvertedIndex(allPages);

        // Create the search engine -> using the index
        SearchEngine searchEngine = new SearchEngine(index);

        // Start the web server -> using the search engine
    }
}

// //Old code from :
//     var filename = Files.readString(Paths.get("config.txt")).strip();
//     new WebServer(PORT, filename);
//   }


