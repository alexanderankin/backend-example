package org.example.backend;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;

public class BackendExample {
    public static void main(String[] args) {
        DisposableServer server = HttpServer.create()
                .route(r -> r.get("/test", (req, res) -> res.sendString(Mono.just("test"))))
                .bindNow();

        HttpClient httpClient = HttpClient.create().baseUrl("http://localhost:" + server.port());

        String test = httpClient.get().uri("/test").responseContent().aggregate().asString().block();
        System.out.println(test); // "test"

        server.disposeNow();
    }
}
