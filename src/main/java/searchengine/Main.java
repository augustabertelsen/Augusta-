package searchengine;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
    
    // read config.txt to get filename
    var filename = Files.readString(Paths.get("config.txt")).strip();
    // create server instance
    new WebServer(PORT, filename);
  }



    }
