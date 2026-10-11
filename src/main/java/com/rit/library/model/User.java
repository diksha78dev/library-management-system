package com.rit.library.model;

import java.time.LocalDateTime;

/**
 * User domain model representing registered library users.
 * Managed via in-memory storage within the application lifecycle.
 */
public class User {

    private String name;
    private String email;
    private String password;
    private LocalDateTime registeredAt;

    public User() {
        this.registeredAt = LocalDateTime.now();
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.registeredAt = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}
