package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point for the search engine application.
 * Sets up all components: loads pages, builds index, creates search engine, and starts web server.
 */
public class Application {
    /**
     * Initializes and starts the search engine application.
     * Reads the data file path from config.txt, loads pages, builds the search index,
     * and sets up the web server components.
     * @throws IOException if the config file or data file cannot be read.
     */
    public void start() throws IOException {
        
        // 1.
        //to test old code 

        // String dataFilePath = Files.readString(Paths.get("config.txt")).strip();
        // PageCollection collection = new PageCollection(dataFilePath);
        
        // OldSearchEngine searh = new OldSearchEngine(collection.getPages());
        // OldQueryHandler handler = new OldQueryHandler(searh);
        // OldServerWeb server = new OldServerWeb(8080, handler);


        //2
        //To test current - whit inverted index, but not run webserver 

        //String dataFilePath = Files.readString(Paths.get("config.txt")).strip();
        //PageCollection collection = new PageCollection(dataFilePath);
        // InvertedIndex index = new InvertedIndex(collection.getPages());
        // SearchEngine search = new SearchEngine(index);
        // List test = new ArrayList();
        // test.add("usa");
        // QueryHandler handler = new QueryHandler();
        // List<List<String>> testtest = handler.handleQuery(test);
        // List<Page> resultPagss = search.search(testtest);
        // search.finalResultBuilder(resultPagss);

        // 3
        // to run current 
        String dataFilePath = Files.readString(Paths.get("config.txt")).strip();
        PageCollection collection = new PageCollection(dataFilePath);
        InvertedIndex index = new InvertedIndex(collection.getPages());
        SearchEngine search = new SearchEngine(index);
        WebServer server = new WebServer(8080, search);


        
    }
    
    /**
     * Main method that creates and starts the application.
     */
    public static void main(String[] args) {
        Application app = new Application();
        try {
            app.start();
        } catch (IOException e) {
            System.err.println("Failed to start application: " + e.getMessage());
            System.exit(1);
        }
    }
}

