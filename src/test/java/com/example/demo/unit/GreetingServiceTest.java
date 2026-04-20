package com.example.demo.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit Tests - Greeting Logic")
class GreetingServiceTest {
    
    @Test
    @DisplayName("Greeting should contain CI/CD")
    void greetingMessageShouldContainCICD() {
        String greeting = generateGreeting();
        assertTrue(greeting.contains("CI/CD"));
    }
    
    @Test
    @DisplayName("Greeting should not be empty")
    void greetingMessageShouldNotBeEmpty() {
        String greeting = generateGreeting();
        assertNotNull(greeting);
        assertFalse(greeting.isEmpty());
    }
    
    @Test
    @DisplayName("Greeting should have minimum length")
    void greetingMessageShouldHaveMinimumLength() {
        String greeting = generateGreeting();
        assertTrue(greeting.length() >= 10);
    }
    
    @Test
    @DisplayName("Greeting should start with Hello")
    void greetingShouldStartWithHello() {
        String greeting = generateGreeting();
        assertTrue(greeting.startsWith("Hello"));
    }
    
    private String generateGreeting() {
        return "Hello CI/CD Pipeline!";
    }
}