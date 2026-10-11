package com.rit.library.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.rit.library.service.AuthService;

/**
 * Unit tests for AuthController using MockMvc.
 * Developed by Girija Ashok Kadam (Developer 1) and Rajsi Rajesh Shinde (Developer 2).
 */
@WebMvcTest(AuthController.class)
@Import(AuthService.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    // ==========================================
    // DEVELOPER 1 TESTS: Login Endpoint
    // ==========================================

    @Test
    @DisplayName("POST /api/auth/login succeeds with valid username credentials")
    void loginWithValidUsernameReturnsSuccessAndToken() throws Exception {
        String requestJson = """
                {
                    "username": "admin",
                    "password": "admin123"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @DisplayName("POST /api/auth/login succeeds with valid email credentials")
    void loginWithValidEmailReturnsSuccessAndToken() throws Exception {
        String requestJson = """
                {
                    "email": "student@rit.ac.in",
                    "password": "student123"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @DisplayName("POST /api/auth/login fails with invalid password")
    void loginWithInvalidPasswordReturnsUnauthorized() throws Exception {
        String requestJson = """
                {
                    "username": "admin",
                    "password": "wrongpassword"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid credentials"))
                .andExpect(jsonPath("$.token").doesNotExist());
    }

    @Test
    @DisplayName("POST /api/auth/login fails with non-existent user")
    void loginWithNonExistentUserReturnsUnauthorized() throws Exception {
        String requestJson = """
                {
                    "username": "nonexistent_user",
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }

    @Test
    @DisplayName("POST /api/auth/login returns bad request when credentials are empty")
    void loginWithMissingCredentialsReturnsBadRequest() throws Exception {
        String requestJson = """
                {
                    "username": "",
                    "password": ""
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Username/email and password are required"));
    }

    @Test
    @DisplayName("POST /api/auth/login works with newly registered user (integration readiness)")
    void loginWithNewlyRegisteredUserSucceeds() throws Exception {
        // Register user into AuthService (e.g., when called by Developer 2's registration endpoint)
        authService.registerUser("rajsi", "rajsi@rit.ac.in", "rajsiPass123");

        String requestJson = """
                {
                    "username": "rajsi",
                    "password": "rajsiPass123"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    // ==========================================
    // DEVELOPER 2 TESTS: Registration Endpoint
    // ==========================================

    @Test
    @DisplayName("POST /api/auth/register succeeds with valid registration request")
    void registerWithValidDataReturnsCreated() throws Exception {
        String requestJson = """
                {
                    "name": "Rajsi Shinde",
                    "email": "rajsi.shinde@rit.ac.in",
                    "password": "SecurePassword123"
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    @DisplayName("POST /api/auth/register rejects missing or empty required fields")
    void registerWithMissingFieldsReturnsBadRequest() throws Exception {
        String requestJson = """
                {
                    "name": "",
                    "email": "",
                    "password": ""
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Name, email, and password are required"));
    }

    @Test
    @DisplayName("POST /api/auth/register rejects invalid email format")
    void registerWithInvalidEmailFormatReturnsBadRequest() throws Exception {
        String requestJson = """
                {
                    "name": "Invalid Email User",
                    "email": "not-a-valid-email",
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid email format"));
    }

    @Test
    @DisplayName("POST /api/auth/register rejects duplicate email address")
    void registerWithDuplicateEmailReturnsConflict() throws Exception {
        // admin@rit.ac.in is already seeded in AuthService
        String requestJson = """
                {
                    "name": "Admin Imposter",
                    "email": "admin@rit.ac.in",
                    "password": "anotherPassword"
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email is already registered"));
    }

    @Test
    @DisplayName("POST /api/auth/register integration: registered user can log in")
    void registerAndThenLoginSucceeds() throws Exception {
        String registerJson = """
                {
                    "name": "Integration Tester",
                    "email": "tester@rit.ac.in",
                    "password": "password123"
                }
                """;

        // Register user
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));

        // Authenticate user via Developer 1's login endpoint
        String loginJson = """
                {
                    "email": "tester@rit.ac.in",
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }
}
