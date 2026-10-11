package com.rit.library.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple health endpoint. Postman (QA), Docker and Kubernetes probes
 * can all use GET /health to check that the app is running.
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "app", "library-management-system");
    }
}
