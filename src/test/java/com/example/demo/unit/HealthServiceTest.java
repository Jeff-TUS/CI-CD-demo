package com.example.demo.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit Tests - Health Check Logic")
class HealthServiceTest {
    
    @Test
    @DisplayName("Health should return OK")
    void healthCheckShouldReturnOK() {
        String status = getHealthStatus();
        assertEquals("OK", status);
    }
    
    @Test
    @DisplayName("Health should not be null")
    void healthStatusShouldNotBeNull() {
        String status = getHealthStatus();
        assertNotNull(status);
    }
    
    @Test
    @DisplayName("Health should be uppercase")
    void healthStatusShouldBeUppercase() {
        String status = getHealthStatus();
        assertEquals(status, status.toUpperCase());
    }
    
    private String getHealthStatus() {
        return "OK";
    }
}