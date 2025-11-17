package com.lcaohoanq.graphqlapi;

import io.github.lcaohoanq.annotations.BrowserLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@BrowserLauncher(
    url = "http://localhost:8082/graphiql.html"
)
public class GraphqlApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GraphqlApiApplication.class, args);
    }
}
