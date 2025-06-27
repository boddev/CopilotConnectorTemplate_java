/main/java/com/example/graphconnector/GraphConnectorApiApplication.java
package com.example.graphconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GraphConnectorApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GraphConnectorApiApplication.class, args);
    }
}