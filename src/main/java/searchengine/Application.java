package searchengine;

import java.io.IOException;
import java.net.BindException;
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
     *
     * @throws IOException   if the config file or data file cannot be read.
     * @throws BindException if port 8080 is already in use.
     */
    public void start() throws IOException, BindException {
        String dataFilePath = Files.readString(Paths.get("config.txt")).strip();
        PageCollection collection = new PageCollection(dataFilePath);
        InvertedIndex index = new InvertedIndex(collection.getPages());
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
        } catch (BindException e) {
            System.err.println("Failed to start application: Port 8080 is already in use");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Failed to start application: " + e.getMessage());
            System.exit(1);
        }
    }
}

