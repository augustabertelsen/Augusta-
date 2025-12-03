package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        // String dataFilePath = Files.readString(Paths.get("config.txt")).strip();
        // PageCollection collection = new PageCollection(dataFilePath);
        // InvertedIndex index = new InvertedIndex(collection.getPages());
        // Ranking rank = new Ranking(index, true);
        // SearchEngine search = new SearchEngine(index, rank);
        // List<String> test = new ArrayList<String>();
        // test.add("usa");
        // test.add("OR");
        // test.add("denmark");
        // QueryHandler handler = new QueryHandler();
        // List<List<String>> testtest = handler.handleQuery(test);
        // List<Page> resultPages = search.search(testtest);
        // List<Page> resultPagesSortedByRank = rank.rankPages(resultPages, testtest);
        
        // List<Page> resultPagss = search.search(testtest);
        // search.finalResultBuilder(resultPagss);

        // 3
        // to run current 
        String dataFilePath = Files.readString(Paths.get("config.txt")).strip();
        PageCollection collection = new PageCollection(dataFilePath);
        InvertedIndex index = new InvertedIndex(collection.getPages());
       // ranking can be switched between complexranking = true, and complexranking = false
        Ranking rank = new Ranking(index, true);
        SearchEngine search = new SearchEngine(index, rank);
        WebServer server = new WebServer(8080, search);
        server.start();
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

