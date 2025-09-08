package org.example.dto;

/**
 * DTO for authentication responses.
 */
public class AuthResponse {
    private String username;
    private String ownerId;
    private String token;
    private boolean authenticated;

    // Default constructor
    public AuthResponse() {
    }

    // Constructor for successful authentication
    public AuthResponse(String username, String ownerId, String token) {
        this.username = username;
        this.ownerId = ownerId;
        this.token = token;
        this.authenticated = true;
    }

    // Constructor for failed authentication
    public AuthResponse(boolean authenticated) {
        this.authenticated = authenticated;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
