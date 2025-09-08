package org.example.model;

/**
 * Class representing a user for authentication.
 * This is a simple in-memory representation for a test application.
 */
public class User {
    private String username;
    private String password;
    private String ownerId;

    // Default constructor
    public User() {
    }

    // Constructor with parameters
    public User(String username, String password, String ownerId) {
        this.username = username;
        this.password = password;
        this.ownerId = ownerId;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
