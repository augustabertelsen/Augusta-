// Starts the HTTP server, sets up routes, 
// serves static files, and forwards /search requests to QueryHandler. 
// It never searches, loads data, builds JSON, or interacts with PageCollection or InvertedIndex.


package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpServer;

// PSEUDO CODE:

class WebServer {
    private static final int PORT = 8080;
    private static final int BACKLOG = 0;
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private HttpServer server;
    private QueryHandler handler;

public WebServer(int port, QueryHandler handler){

 // pseudo code:
    //     store handler
    //     create HttpServer with port + BACKLOG
    //     register static file routes
    //     register /search route → call handler.handleSearch(...)
    //     start server
    //     print startup banner

}

//methods psuedo code: 
    getFile(filename) {
        // read file bytes and return them
    }

    respond() {
        // set headers
        // write response bytes
        // close exchange
    }
}







//OldWebServer code:

// public class WebServer {

//     // constants
//     static final int PORT = 8080;
//     static final int BACKLOG = 0;
//     static final Charset CHARSET = StandardCharsets.UTF_8;
    
//     // server field
//     HttpServer server;

//     // needs field: QueryHandler handler


// public WebServer(int port, String filename)throws IOException{

//     //HttpServer setup 
//     server = HttpServer.create(new InetSocketAddress(port), BACKLOG);

//     //static file endpoints
//     server.createContext("/", io -> respond(io, 200, "text/html", getFile("web/index.html")));
//     server.createContext("/search", io -> search(io)); 
//     server.createContext("/favicon.ico", io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
//     server.createContext("/code.js", io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
//     server.createContext("/style.css", io -> respond(io, 200, "text/css", getFile("web/style.css")));
    

//      // server start + banner
//         server.start();
//         String msg = " WebServer running on http://localhost:" + port + " ";
//         System.out.println("╭"+"─".repeat(msg.length())+"╮");
//         System.out.println("│"+msg+"│");
//         System.out.println("╰"+"─".repeat(msg.length())+"╯");

// }


// // write HTTP response
//     var bytes = response.toString().getBytes(CHARSET);
//     respond(io, 200, "application/json", bytes);
//   }


//   // reading static file bytes
//   byte[] getFile(String filename) {
//     try {
//       return Files.readAllBytes(Paths.get(filename));
//     } catch (IOException e) {
//       e.printStackTrace();
//       return new byte[0];
//     }



// // writing HTTP response 
//   void respond(HttpExchange io, int code, String mime, byte[] response) {
//     try {
//       io.getResponseHeaders()
//           .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
//       io.sendResponseHeaders(200, response.length);
//       io.getResponseBody().write(response);
//     } catch (Exception e) {
//     } finally {
//       io.close();
//     }
//   }


  









