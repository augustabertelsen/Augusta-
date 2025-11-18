package searchengine;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class WebServer {

      // default port
    static final int PORT = 8080;
    // backlog size for queued connections
    static final int BACKLOG = 0;
    // character encoding used for all text
    static final Charset CHARSET = StandardCharsets.UTF_8;

    // HTTP server instance
    HttpServer server;

 // constructor: loads file + builds HTTP handlers + starts server
  WebServer(int port, String filename){

  }


 // create HTTP server bound to port
    server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
    // add context: root → serve index.html
    server.createContext("/", io -> respond(io, 200, "text/html", getFile("web/index.html")));
    // add context: /search → run search method
    server.createContext("/search", io -> search(io));
    // add context: browser icon
    server.createContext("/favicon.ico",
      io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
    // add context: JS file
    server.createContext("/code.js",
      io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
    // add context: CSS file
    server.createContext("/style.css",
      io -> respond(io, 200, "text/css", getFile("web/style.css")));
    // start the HTTP server
    server.start();

    // print startup banner
    String msg = " WebServer running on http://localhost:" + port + " ";
    System.out.println("╭" + "─".repeat(msg.length()) + "╮");
    System.out.println("│" + msg + "│");
    System.out.println("╰" + "─".repeat(msg.length()) + "╯");



     // read entire file as bytes
  byte[] getFile(String filename) {
    try {
      // load file from disk
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      // print error and return empty byte array
      e.printStackTrace();
      return new byte[0];
    }
  }

  // send HTTP response with headers + body
  void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      // set MIME type + charset
      io.getResponseHeaders()
        .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      // send status code + length
      io.sendResponseHeaders(200, response.length); // ignores 'code' parameter
      // write body
      io.getResponseBody().write(response);
    } catch (Exception e) {
      // swallow — bad practice
    } finally {
      // always close exchange
      io.close();
    }
  }




}
