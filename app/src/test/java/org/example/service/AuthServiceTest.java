package org.example.service;

import org.example.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    private final String validUsername = "15.413.217-1";
    private final String validPassword = "AiPuedaexeeb0ei";
    private final String validOwnerId = "12345678";
    
    private User testUser;

    @Before
    public void setUp() {
        testUser = new User(validUsername, validPassword, validOwnerId);
    }

    @Test
    public void testAuthenticate_WithValidCredentials() {
        // Act
        User authenticatedUser = authService.authenticate(validUsername, validPassword);
        
        // Assert
        assertNotNull(authenticatedUser);
        assertEquals(validUsername, authenticatedUser.getUsername());
        assertEquals(validPassword, authenticatedUser.getPassword());
        assertEquals(validOwnerId, authenticatedUser.getOwnerId());
    }
    
    @Test
    public void testAuthenticate_WithInvalidUsername() {
        // Act
        User authenticatedUser = authService.authenticate("invalid-username", validPassword);
        
        // Assert
        assertNull(authenticatedUser);
    }
    
    @Test
    public void testAuthenticate_WithInvalidPassword() {
        // Act
        User authenticatedUser = authService.authenticate(validUsername, "invalid-password");
        
        // Assert
        assertNull(authenticatedUser);
    }
    
    @Test
    public void testGenerateToken() {
        // Act
        String token = authService.generateToken(testUser);
        
        // Assert
        assertNotNull(token);
        
        // Verify token is stored in the activeTokens map
        String ownerId = authService.validateToken(token);
        assertEquals(validOwnerId, ownerId);
    }
    
    @Test
    public void testValidateToken_WithValidToken() throws Exception {
        // Arrange
        String testToken = "test-token";
        
        // Access the private activeTokens field using reflection
        Field activeTokensField = AuthService.class.getDeclaredField("activeTokens");
        activeTokensField.setAccessible(true);
        
        Map<String, String> activeTokens = (Map<String, String>) activeTokensField.get(authService);
        activeTokens.put(testToken, validOwnerId);
        
        // Act
        String result = authService.validateToken(testToken);
        
        // Assert
        assertEquals(validOwnerId, result);
    }
    
    @Test
    public void testValidateToken_WithInvalidToken() {
        // Act
        String result = authService.validateToken("invalid-token");
        
        // Assert
        assertNull(result);
    }
    
    @Test
    public void testInvalidateToken() throws Exception {
        // Arrange
        String testToken = "test-token";
        
        // Access the private activeTokens field using reflection
        Field activeTokensField = AuthService.class.getDeclaredField("activeTokens");
        activeTokensField.setAccessible(true);
        
        Map<String, String> activeTokens = (Map<String, String>) activeTokensField.get(authService);
        activeTokens.put(testToken, validOwnerId);
        
        // Verify token is initially valid
        assertEquals(validOwnerId, authService.validateToken(testToken));
        
        // Act
        authService.invalidateToken(testToken);
        
        // Assert
        assertNull(authService.validateToken(testToken));
    }
    
    @Test
    public void testFullAuthenticationFlow() {
        // Step 1: Authenticate with valid credentials
        User authenticatedUser = authService.authenticate(validUsername, validPassword);
        assertNotNull(authenticatedUser);
        
        // Step 2: Generate token for the authenticated user
        String token = authService.generateToken(authenticatedUser);
        assertNotNull(token);
        
        // Step 3: Validate the token
        String ownerId = authService.validateToken(token);
        assertEquals(validOwnerId, ownerId);
        
        // Step 4: Invalidate the token
        authService.invalidateToken(token);
        
        // Step 5: Verify the token is no longer valid
        assertNull(authService.validateToken(token));
    }
}
