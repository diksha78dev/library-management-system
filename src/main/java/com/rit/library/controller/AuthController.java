package com.rit.library.controller;

import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rit.library.dto.AuthResponse;
import com.rit.library.dto.LoginRequest;
import com.rit.library.dto.RegistrationRequest;
import com.rit.library.service.AuthService;

/**
 * Authentication REST controller for Library Management System.
 * Developed by Girija Ashok Kadam (Developer 1) and Rajsi Rajesh Shinde (Developer 2).
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/login
     * Authenticates user with username or email and password.
     * Developed by Girija Ashok Kadam (Developer 1).
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

    /**
     * POST /api/auth/register
     * Registers a new user with name, email, and password.
     * Developed by Rajsi Rajesh Shinde (Developer 2).
     *
     * @param registrationRequest payload containing name, email, and password
     * @return AuthResponse with success status and message
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody(required = false) RegistrationRequest registrationRequest) {
        if (registrationRequest == null
                || registrationRequest.getName() == null || registrationRequest.getName().trim().isEmpty()
                || registrationRequest.getEmail() == null || registrationRequest.getEmail().trim().isEmpty()
                || registrationRequest.getPassword() == null || registrationRequest.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Name, email, and password are required", null));
        }

        String email = registrationRequest.getEmail().trim();
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Invalid email format", null));
        }

        if (authService.isEmailRegistered(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(false, "Email is already registered", null));
        }

        boolean registered = authService.register(registrationRequest);
        if (registered) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(true, "User registered successfully", null));
        } else {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Registration failed", null));
        }
    }

    public AuthService getAuthService() {
        return authService;
    }
}
