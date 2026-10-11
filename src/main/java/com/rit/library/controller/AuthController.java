package com.rit.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rit.library.dto.AuthResponse;
import com.rit.library.dto.LoginRequest;
import com.rit.library.service.AuthService;

/**
 * Authentication REST controller for Library Management System.
 * Developed by Girija Ashok Kadam (Developer 1).
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/login
     * Authenticates user with username or email and password.
     *
     * @param loginRequest credentials JSON containing username/email and password
     * @return AuthResponse with success status, message, and dummy auth token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody(required = false) LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getIdentifier() == null || loginRequest.getPassword() == null
                || loginRequest.getIdentifier().trim().isEmpty() || loginRequest.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Username/email and password are required", null));
        }

        AuthResponse response = authService.authenticate(loginRequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    public AuthService getAuthService() {
        return authService;
    }
}
