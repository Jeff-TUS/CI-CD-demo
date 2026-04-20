package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello CI/CD Pipeline!";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/bad-practice")
    public String badPractice() {
        try {
            int x = 5 / 0;
        } catch (Exception e) {
            // Empty catch block - SonarQube blocker issue
        }
        String password = "admin123";
        return "Bad practice endpoint";
    }
}