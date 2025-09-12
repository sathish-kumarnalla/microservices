package com.techie.gateway.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RoutingService {

    private final RestTemplate restTemplate;

    public RoutingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Routes request with optional circuit breaker.
     */
    public ResponseEntity<String> route(String service, String path, boolean useCircuitBreaker) {
        if (useCircuitBreaker && "user-profile-service".equalsIgnoreCase(service)) {
            return routeWithCircuitBreaker(service, path);
        }
        return callService(service, path);
    }

    /**
     * Circuit breaker protected route.
     */
    @CircuitBreaker(name = "userProfileServiceCB", fallbackMethod = "fallbackUserProfile")
    public ResponseEntity<String> routeWithCircuitBreaker(String service, String path) {
        return callService(service, path);
    }

    /**
     * Shared logic to call downstream service.
     */
    private ResponseEntity<String> callService(String service, String path) {
        String url = String.format("http://%s/%s", service, path);
        return restTemplate.getForEntity(url, String.class);
    }

    /**
     * Fallback method for circuit breaker.
     */
    public ResponseEntity<String> fallbackUserProfile(String service, String path, Throwable ex) {
        String message = String.format("Fallback: %s is currently unavailable.", service);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(message);
    }
}