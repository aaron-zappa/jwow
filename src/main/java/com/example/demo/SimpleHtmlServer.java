package com.example.demo;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class SimpleHtmlServer {

    private static final int PORT = 5003;
    private static final String STATIC_DIR = "src/main/resources/static";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", new StaticFileHandler());
        server.createContext("/callHelloWorld", new CallHelloWorldHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port " + PORT);
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String uri = t.getRequestURI().getPath();
            if (uri.equals("/")) {
                uri = "/index.html"; // Serve index.html by default
            }

            Path filePath = Paths.get(STATIC_DIR, uri);
            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream"; // Default to binary if type is unknown
                }
                t.getResponseHeaders().set("Content-Type", contentType);
                t.sendResponseHeaders(200, Files.size(filePath));
                try (OutputStream os = t.getResponseBody()) {
                    Files.copy(filePath, os);
                }
            } else {
                String response = "404 Not Found";
                t.sendResponseHeaders(404, response.length());
                try (OutputStream os = t.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }

    static class CallHelloWorldHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            HelloWorld helloWorld = new HelloWorld();
            String response = helloWorld.sayHello();

            t.getResponseHeaders().set("Content-Type", "text/plain");
            t.sendResponseHeaders(200, response.length());
            try (OutputStream os = t.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}