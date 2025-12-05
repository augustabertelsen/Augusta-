package searchengine;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * A web server that hosts the search engine UI and handles HTTP requests.
 * <p>
 * This server provides static routes for providing files to the UI (HTML, CSS, JS, favicon)
 * and a /search route that provides results from the SearchEngine class.
 */
class WebServer {
    private static final int BACKLOG = 0;
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private final HttpServer server;

    /**
     * Creates a new WebServer that listens on the specified port and uses the provided SearchEngine.
     *
     * @param port   the port number on which the server will listen
     * @param search the SearchEngine object to handle search requests
     * @throws IOException   if an I/O error occurs when creating the HttpServer
     * @throws BindException if the port is already in use
     */
    public WebServer(int port, SearchEngine search) throws IOException, BindException {

        server = HttpServer.create(new InetSocketAddress(port), BACKLOG);

        // The static routes (/, CSS, JS, favicon) just return files, so they don’t need QueryHandler -> uses getFile to get byte for respond 
        server.createContext("/", io -> respond(io, 200, "text/html", getFile("web/index.html")));
        server.createContext("/favicon.ico", io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
        server.createContext("/code.js", io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
        server.createContext("/style.css", io -> respond(io, 200, "text/css", getFile("web/style.css")));

        // /search is not a file,it triggers real search logic.
        // Only QueryHandler knows how to process a query and return JSON, so only /search uses it.
        server.createContext("/search", io -> respond(io, 200, "application/json", search.handleSearch(extractRawQuery(io))));
    }

    /**
     * Starts the web server and displays a formatted message with the server URL.
     */
    public void start() {
        server.start();
        String msg = " WebServer running on http://localhost:" + server.getAddress().getPort() + " ";
        System.out.println("╭" + "─".repeat(msg.length()) + "╮");
        System.out.println("│" + msg + "│");
        System.out.println("╰" + "─".repeat(msg.length()) + "╯");
    }

    /**
     * Stops the web server if the server object is not null.
     */
    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    /**
     * @return the port number this server is running on
     */
    public int getPort() {
        return server.getAddress().getPort();
    }

    /**
     * Reads a file from the filesystem and returns its contents as a byte array.
     *
     * @param filename the path to the file to be read
     * @return the file contents as a byte array, or an empty array if the file cannot be read
     */
    byte[] getFile(String filename) {
        try {
            return Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * Sends a HTTP response with the specified status code, content type, and response body.
     *
     * @param io       the HttpExchange to send the response through
     * @param code     the HTTP status code to send
     * @param mime     the MIME type of the response content
     * @param response the response data as a byte array to put in the body
     */
    private void respond(HttpExchange io, int code, String mime, byte[] response) {
        try (io) {
            io.getResponseHeaders()
                    .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
            io.sendResponseHeaders(code, response.length);
            io.getResponseBody().write(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts the raw query (containing search term) from the HttpExchange.
     * For example the request http://localhost:8080/search?q=dog+walk will return the String "dog+walk"
     *
     * @param io HttpExchange containing the search request
     * @return String containing the raw query value after the "q=" parameter
     */
    private String extractRawQuery(HttpExchange io) {
        return io.getRequestURI().getRawQuery().split("=")[1];
    }

}


  









