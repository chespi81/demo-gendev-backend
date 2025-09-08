package org.example.service;

import org.example.model.User;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for handling user authentication.
 */
@Service
public class AuthService {
    
    // In a real application, this would be stored in a database
    private static final User VALID_USER = new User("15.413.217-1", "AiPuedaexeeb0ei", "12345678");
    
    // Simple in-memory token storage (in a real application, this would use a more robust solution)
    private final Map<String, String> activeTokens = new ConcurrentHashMap<>();
    
    /**
     * Authenticates a user with the provided credentials.
     * 
     * @param username the username
     * @param password the password
     * @return the authenticated user if credentials are valid, null otherwise
     */
    public User authenticate(String username, String password) {
        // Check if the provided credentials match the valid user
        if (VALID_USER.getUsername().equals(username) && VALID_USER.getPassword().equals(password)) {
            return VALID_USER;
        }
        return null;
    }
    
    /**
     * Generates a token for the authenticated user.
     * 
     * @param user the authenticated user
     * @return the generated token
     */
    public String generateToken(User user) {
        // Generate a random token
        String token = UUID.randomUUID().toString();
        
        // Store the token with the user's ownerId
        activeTokens.put(token, user.getOwnerId());
        
        return token;
    }
    
    /**
     * Validates a token and returns the associated ownerId.
     * 
     * @param token the token to validate
     * @return the ownerId associated with the token, or null if the token is invalid
     */
    public String validateToken(String token) {
        return activeTokens.get(token);
    }
    
    /**
     * Invalidates a token.
     * 
     * @param token the token to invalidate
     */
    public void invalidateToken(String token) {
        activeTokens.remove(token);
    }
}
