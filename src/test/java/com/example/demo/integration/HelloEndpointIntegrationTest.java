package com.example.demo.integration;

import com.example.demo.DemoApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DemoApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Integration Tests - Hello Endpoint")
class HelloEndpointIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestClient restClient = RestClient.create();

    @Test
    @DisplayName("GET /hello returns 200 with greeting")
    void helloEndpointShouldReturnGreeting() {
        String url = "http://localhost:" + port + "/hello";

        ResponseEntity<String> response = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Hello").contains("CI/CD");
    }

    @Test
    @DisplayName("GET /hello returns text content")
    void helloEndpointShouldReturnTextContent() {
        String url = "http://localhost:" + port + "/hello";

        ResponseEntity<String> response = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);

        assertThat(response.getHeaders().getContentType().toString()).contains("text/plain");
    }

    @Test
    @DisplayName("GET /hello responds within 500ms")
    void helloEndpointShouldRespondQuickly() {
        String url = "http://localhost:" + port + "/hello";

        long start = System.currentTimeMillis();
        ResponseEntity<String> response = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);
        long duration = System.currentTimeMillis() - start;

        assertThat(duration).isLessThan(500);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}