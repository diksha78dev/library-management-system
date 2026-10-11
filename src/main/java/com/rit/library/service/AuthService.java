package com.rit.library.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.rit.library.dto.AuthResponse;
import com.rit.library.dto.LoginRequest;
import com.rit.library.dto.RegistrationRequest;
import com.rit.library.model.User;

/**
 * Authentication service handling credentials validation, registration, and token generation.
 * Built with an in-memory store for smooth integration between Developer 1 and Developer 2.
 *
 * Persistence Architecture Note:
 * This project utilizes an in-memory storage strategy (ConcurrentHashMap) for both user credentials
 * and registered user entities.
 * Limitations of this architecture:
 * 1. Volatility: All registered users and session data reside in JVM heap memory and are lost upon process restart or crash.
 * 2. Scalability: Multiple server/container instances cannot share state without an external distributed store or database.
 * 3. Concurrency & ACID: While ConcurrentHashMap provides thread safety for individual operations, it lacks multi-entity transactional guarantees.
 */
@Service
public class AuthService {

    // In-memory store for registered credentials: username/email -> password
    private final Map<String, String> userCredentials = new ConcurrentHashMap<>();

    // In-memory store for registered User entities: email (lowercase) -> User
    private final Map<String, User> registeredUsers = new ConcurrentHashMap<>();

    public AuthService() {
        // Pre-configure demo credentials for local development, testing, and evaluation
        registerUser("admin", "admin@rit.ac.in", "admin123");
        registerUser("student", "student@rit.ac.in", "student123");
        registerUser("girija", "girija@rit.ac.in", "girija123");

        registeredUsers.put("admin@rit.ac.in", new User("Admin", "admin@rit.ac.in", "admin123"));
        registeredUsers.put("student@rit.ac.in", new User("Student", "student@rit.ac.in", "student123"));
        registeredUsers.put("girija@rit.ac.in", new User("Girija Ashok Kadam", "girija@rit.ac.in", "girija123"));
    }

    /**
     * Checks whether an email address is already registered in the system.
     *
     * @param email email address to check
     * @return true if the email is already registered, false otherwise
     */
    public boolean isEmailRegistered(String email) {
        if (email == null) {
            return false;
        }
        String normalizedEmail = email.trim().toLowerCase();
        return registeredUsers.containsKey(normalizedEmail) || userCredentials.containsKey(normalizedEmail);
    }

    /**
     * Registers a new user from a RegistrationRequest.
     * Developed by Rajsi Rajesh Shinde (Developer 2).
     *
     * @param request registration request containing name, email, and password
     * @return true if registration succeeded, false if validation failed or email already registered
     */
    public synchronized boolean register(RegistrationRequest request) {
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            return false;
        }

        String normalizedEmail = request.getEmail().trim().toLowerCase();
        if (isEmailRegistered(normalizedEmail)) {
            return false;
        }

        User user = new User(request.getName(), normalizedEmail, request.getPassword());
        registeredUsers.put(normalizedEmail, user);

        // Keep credentials store synchronized for Developer 1 login API
        registerUser(request.getName(), normalizedEmail, request.getPassword());
        return true;
    }

    /**
     * Registers a new user. Suitable for Developer 2's registration endpoint integration.
     * Original method created by Girija Ashok Kadam (Developer 1).
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
     * Original method created by Girija Ashok Kadam (Developer 1).
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
