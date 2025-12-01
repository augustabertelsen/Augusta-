package searchengine.OldFiles;
// package searchengine;
    
//     import java.io.IOException;
//     import java.net.InetSocketAddress;
//     import java.nio.charset.Charset;
//     import java.nio.charset.StandardCharsets;
//     import java.nio.file.Files;
//     import java.nio.file.Paths;
    
//     import com.sun.net.httpserver.HttpExchange;
//     import com.sun.net.httpserver.HttpServer;
    

// public class OldServerWeb {
//         private static final int PORT = 8080;
//         private static final int BACKLOG = 0;
//         private static final Charset CHARSET = StandardCharsets.UTF_8;
    
//         private HttpServer server;
//         private OldQueryHandler handler;
    
//         public OldServerWeb(int port, OldQueryHandler handler) throws IOException {
//             //store handler
//             this.handler = handler;
    
//             //HttpServer setup 
    
//             //create HttpServer with port + BACKLOG
//             server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
    
            
            
//             // The static routes (/, CSS, JS, favicon) just return files, so they don’t need QueryHandler -> uses getFile to get byte for respond 
//             server.createContext("/", io -> respond(io, 200, "text/html", getFile("web/index.html")));
//             server.createContext("/favicon.ico", io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
//             server.createContext("/code.js", io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
//             server.createContext("/style.css", io -> respond(io, 200, "text/css", getFile("web/style.css")));
            
    
//             // /search is not a file,it triggers real search logic.
//             // Only QueryHandler knows how to process a query and return JSON, so only /search uses it.
//             server.createContext("/search", io -> respond(io, 200, "application/json", handler.handleSearch(io))); 
           
    
    
//             // server start + banner
//             server.start();
//             String msg = " WebServer running on http://localhost:" + port + " ";
//             System.out.println("╭"+"─".repeat(msg.length())+"╮");
//             System.out.println("│"+msg+"│");
//             System.out.println("╰"+"─".repeat(msg.length())+"╯");
    
    
//         }
    
//         // getFile() reads the file at the given filename and returns its contents as a byte[].
//         // If the file can’t be read, it returns an empty byte array instead of crashing. 
    
//         private byte[] getFile(String filename){
//                 // try/catch prevents the server from crashing if the file is missing
//                 try { 
//                 // correctly loads a file into a byte[]  (Files and Paths are standard java object classes)
//                 return Files.readAllBytes(Paths.get(filename));
//                 } catch (IOException e) {
//                 e.printStackTrace();
//                 // safely returns an empty response instead of null
//                 return new byte[0];
//             }
//         } 

//           private void respond(HttpExchange io,int code, String mime, byte[] response) {
//         try (io) {
//         io.getResponseHeaders()
//             .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
//         io.sendResponseHeaders(code, response.length);
//         io.getResponseBody().write(response);
//             } catch (Exception e) {
//                 // write catch block
//             }
//     }

    
    
// }


