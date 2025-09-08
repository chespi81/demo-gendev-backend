package org.example.controller;

import org.example.dto.AuthRequest;
import org.example.dto.AuthResponse;
import org.example.model.User;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that provides endpoints for user authentication.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticate a user with username and password.
     * 
     * @param authRequest the authentication request containing username and password
     * @return authentication response with token and user details if successful
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        // Authenticate user
        User user = authService.authenticate(authRequest.getUsername(), authRequest.getPassword());
        
        if (user != null) {
            // Generate token
            String token = authService.generateToken(user);
            
            // Create successful response
            AuthResponse response = new AuthResponse(user.getUsername(), user.getOwnerId(), token);
            return ResponseEntity.ok(response);
        } else {
            // Create failed response
            AuthResponse response = new AuthResponse(false);
            return ResponseEntity.status(401).body(response);
        }
    }

    /**
     * Validate a token.
     * 
     * @param token the token to validate
     * @return OK if token is valid, UNAUTHORIZED otherwise
     */
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestParam String token) {
        String ownerId = authService.validateToken(token);
        
        if (ownerId != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Logout a user by invalidating their token.
     * 
     * @param token the token to invalidate
     * @return OK if token was invalidated
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String token) {
        authService.invalidateToken(token);
        return ResponseEntity.ok().build();
    }
}
