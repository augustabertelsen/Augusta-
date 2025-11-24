package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Application {
    public void start() throws IOException {
        String dataFilePath = Files.readString(Paths.get("config.txt")).strip();
        PageCollection collection = new PageCollection(dataFilePath);
        InvertedIndex index = new InvertedIndex(collection.getPages());
        SearchEngine searchEngine = new SearchEngine(index);
        QueryHandler queryHandler = new QueryHandler(searchEngine);
        WebServer server = new WebServer(8080, queryHandler);
        //server.start(); // Commented out since it's not yet working (due to the WebServer class not being finished)
    }

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

