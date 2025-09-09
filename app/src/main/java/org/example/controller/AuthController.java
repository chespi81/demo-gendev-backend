package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
@Tag(name = "Authentication", description = "User authentication API")
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
    @Operation(summary = "Authenticate user", description = "Authenticates a user with username and password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentication successful", 
                content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "401", description = "Authentication failed", 
                content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "Authentication credentials", required = true) @RequestBody AuthRequest authRequest) {
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
    @Operation(
        summary = "Validate token", 
        description = "Validates a JWT token",
        security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token is valid"),
        @ApiResponse(responseCode = "401", description = "Token is invalid", content = @Content)
    })
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(
            @Parameter(description = "JWT token to validate", required = true, in = ParameterIn.QUERY) 
            @RequestParam String token) {
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
    @Operation(
        summary = "Logout user", 
        description = "Invalidates a user's JWT token",
        security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully logged out")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Parameter(description = "JWT token to invalidate", required = true, in = ParameterIn.QUERY) 
            @RequestParam String token) {
        authService.invalidateToken(token);
        return ResponseEntity.ok().build();
    }
}
