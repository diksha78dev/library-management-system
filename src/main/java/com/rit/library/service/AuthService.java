package com.rit.library.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.rit.library.dto.AuthResponse;
import com.rit.library.dto.LoginRequest;

/**
 * Authentication service handling credentials validation and token generation.
 * Built with an in-memory store for smooth integration with Developer 2's registration API.
 */
@Service
public class AuthService {

    // In-memory store for registered credentials: username/email -> password
    private final Map<String, String> userCredentials = new ConcurrentHashMap<>();

    public AuthService() {
        // Pre-configure demo credentials for local development, testing, and evaluation
        registerUser("admin", "admin@rit.ac.in", "admin123");
        registerUser("student", "student@rit.ac.in", "student123");
        registerUser("girija", "girija@rit.ac.in", "girija123");
    }

    /**
     * Registers a new user. Suitable for Developer 2's registration endpoint integration.
     *
     * @param username username of the user
     * @param email    email of the user
     * @param password raw password
     * @return true if registered successfully, false otherwise
     */
    public synchronized boolean registerUser(String username, String email, String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        if (username != null && !username.trim().isEmpty()) {
            userCredentials.put(username.trim().toLowerCase(), password);
        }
        if (email != null && !email.trim().isEmpty()) {
            userCredentials.put(email.trim().toLowerCase(), password);
        }
        return true;
    }

    /**
     * Authenticates login credentials and produces an AuthResponse with dummy JWT token.
     *
     * @param loginRequest login request credentials
     * @return AuthResponse with success status, message, and dummy auth token
     */
    public AuthResponse authenticate(LoginRequest loginRequest) {
        if (loginRequest == null) {
            return new AuthResponse(false, "Invalid login request", null);
        }

        String identifier = loginRequest.getIdentifier();
        String password = loginRequest.getPassword();

        if (identifier == null || identifier.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return new AuthResponse(false, "Username/email and password are required", null);
        }

        String storedPassword = userCredentials.get(identifier.trim().toLowerCase());
        if (storedPassword != null && storedPassword.equals(password)) {
            String dummyToken = "dummy-jwt-token-" + UUID.randomUUID();
            return new AuthResponse(true, "Login successful", dummyToken);
        }

        return new AuthResponse(false, "Invalid credentials", null);
    }
}
