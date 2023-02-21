package org.example.backend;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class BackendExample {
    public static void main(String[] args) throws Throwable {
        HttpServer server = HttpServer.create()
                .createContext("/test", exchange -> {
                    String response = "response";
                    exchange.sendResponseHeaders(200, response.length());
                    exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
                })
                .getServer();

        server.bind(new InetSocketAddress(8080), 0);
        server.start();

        int port = server.getAddress().getPort();

        String body = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                                .GET()
                                .uri(URI.create("http://localhost:" + port + "/test"))
                                .build(),
                        HttpResponse.BodyHandlers.ofString())
                .body();

        System.out.println(body); // "response"

        server.stop(0);
    }
}
