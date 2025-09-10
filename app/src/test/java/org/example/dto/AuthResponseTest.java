package org.example.dto;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the AuthResponse class
 */
public class AuthResponseTest {
    
    private AuthResponse dto;
    private String testUsername;
    private String testOwnerId;
    private String testToken;
    private boolean testAuthenticated;
    
    @Before
    public void setUp() {
        testUsername = "testUser";
        testOwnerId = "owner123";
        testToken = "jwt.token.example";
        testAuthenticated = true;
        
        // Create a DTO instance with default constructor
        dto = new AuthResponse();
    }
    
    @Test
    public void testDefaultConstructor() {
        // Verify that a DTO is created properly with the default constructor
        assertNotNull(dto);
        assertNull(dto.getUsername());
        assertNull(dto.getOwnerId());
        assertNull(dto.getToken());
        assertFalse(dto.isAuthenticated());
    }
    
    @Test
    public void testSuccessConstructor() {
        // Create a DTO using the success constructor
        AuthResponse successDto = new AuthResponse(testUsername, testOwnerId, testToken);
        
        // Verify all values are set correctly for a successful authentication
        assertEquals(testUsername, successDto.getUsername());
        assertEquals(testOwnerId, successDto.getOwnerId());
        assertEquals(testToken, successDto.getToken());
        assertTrue(successDto.isAuthenticated());
    }
    
    @Test
    public void testFailureConstructor() {
        // Create a DTO using the failure constructor
        boolean failedAuth = false;
        AuthResponse failureDto = new AuthResponse(failedAuth);
        
        // Verify the authentication status is set correctly for a failed authentication
        assertEquals(failedAuth, failureDto.isAuthenticated());
        assertNull(failureDto.getUsername());
        assertNull(failureDto.getOwnerId());
        assertNull(failureDto.getToken());
    }
    
    @Test
    public void testUsernameGetterSetter() {
        dto.setUsername(testUsername);
        assertEquals(testUsername, dto.getUsername());
        
        // Test with another value
        String newUsername = "newTestUser";
        dto.setUsername(newUsername);
        assertEquals(newUsername, dto.getUsername());
    }
    
    @Test
    public void testOwnerIdGetterSetter() {
        dto.setOwnerId(testOwnerId);
        assertEquals(testOwnerId, dto.getOwnerId());
        
        // Test with another value
        String newOwnerId = "newOwner456";
        dto.setOwnerId(newOwnerId);
        assertEquals(newOwnerId, dto.getOwnerId());
    }
    
    @Test
    public void testTokenGetterSetter() {
        dto.setToken(testToken);
        assertEquals(testToken, dto.getToken());
        
        // Test with another value
        String newToken = "new.jwt.token.example";
        dto.setToken(newToken);
        assertEquals(newToken, dto.getToken());
    }
    
    @Test
    public void testAuthenticatedGetterSetter() {
        dto.setAuthenticated(testAuthenticated);
        assertEquals(testAuthenticated, dto.isAuthenticated());
        
        // Test with opposite value
        boolean newAuthenticated = false;
        dto.setAuthenticated(newAuthenticated);
        assertEquals(newAuthenticated, dto.isAuthenticated());
    }
}
