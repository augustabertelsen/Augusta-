
// package searchengine;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.TestInstance.Lifecycle;

// import java.io.IOException;
// import java.net.BindException;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse.BodyHandlers;
// import java.util.Random;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.junit.jupiter.api.TestInstance.Lifecycle;

// @TestInstance(Lifecycle.PER_CLASS)
// class WebServerTest {
//     WebServer server = null;

//     @Disabled
//     @BeforeAll
//     void setUp() {
//         try {
//             PageCollection collection = new PageCollection("data/test-file.txt");
//             InvertedIndex index = new InvertedIndex(collection.getPages());
//             SearchEngine searchEngine = new SearchEngine(index);

//             var rnd = new Random();
//             while (server == null) {
//                 try {
//                     server = new WebServer(rnd.nextInt(60000) + 1024, searchEngine);
//                 } catch (BindException e) {
//                     // port in use. Try again
//                 }
//             }

//             server.start();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     @Disabled
//     @AfterAll
//     void tearDown() {
//         if (server != null) {
//             server.stop();
//             server = null;
//         }
//     }

//     @Disabled
//     @Test
//     void lookupWebServer() {
//         String baseURL = String.format("http://localhost:%d/search?q=", server.getPort());
//         assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"title1\"}, {\"url\": \"http://page2.com\", \"title\": \"title2\"}]",
//                 httpGet(baseURL + "word1"));
//         assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"title1\"}]",
//                 httpGet(baseURL + "word2"));
//         assertEquals("[{\"url\": \"http://page2.com\", \"title\": \"title2\"}]",
//                 httpGet(baseURL + "word3"));
//         assertEquals("[]",
//                 httpGet(baseURL + "word4"));
//     }

//     private String httpGet(String url) {
//         var uri = URI.create(url);
//         var client = HttpClient.newHttpClient();
//         var request = HttpRequest.newBuilder().uri(uri).GET().build();
//         try {
//             return client.send(request, BodyHandlers.ofString()).body();
//         } catch (IOException | InterruptedException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }

//     @Disabled
//     @Test
//     void staticFileRoutes_areServed() {
//         String baseURL = String.format("http://localhost:%d", server.getPort());

//         String indexRequest = baseURL + "/";
//         String indexResponse = httpGet(indexRequest);
//         assertNotNull(indexResponse);

//         String cssRequest = baseURL + "/style.css";
//         String cssResponse = httpGet(cssRequest);
//         assertNotNull(cssResponse);

//         String jsRequest = baseURL + "/code.js";
//         String jsResponse = httpGet(jsRequest);
//         assertNotNull(jsResponse);

//         String iconRequest = baseURL + "/favicon.ico";
//         String iconResponse = httpGet(iconRequest);
//         assertNotNull(iconResponse);
//     }

//     @Disabled
//     @Test
//     void getFile_catchBlock_nonExistentFile() {
//         byte[] result = server.getFile("nonexistent/path/file.txt");
        
//         assertNotNull(result);
//         assertEquals(0, result.length);
//     }
// }
