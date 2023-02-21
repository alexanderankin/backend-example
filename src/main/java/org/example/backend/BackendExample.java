package org.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BackendExample {
    public static void main(String[] args) {
        // set random port on startup, defaults to 8080
        System.setProperty("server.port", "0");
        SpringApplication.run(BackendExample.class, args);
    }

    @Autowired
    Environment environment;

    // this could have actually gone right inside the main method; it's in a method for easy removal
    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            RestTemplate restTemplate = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + environment.getProperty("local.server.port")) // "lsp" is special value for reading the random port
                    .build();
            String responseBody = restTemplate.getForObject("/", String.class);
            System.out.println(responseBody); // world
        };
    }

    @RestController
    public static class ExampleController {
        @GetMapping
        String hi() {
            return "world";
        }
    }
}
