package org.example.dto;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the AuthRequest class
 */
public class AuthRequestTest {
    
    private AuthRequest dto;
    private String testUsername;
    private String testPassword;
    
    @Before
    public void setUp() {
        testUsername = "testUser";
        testPassword = "testPass123";
        
        // Create a DTO instance with default constructor
        dto = new AuthRequest();
    }
    
    @Test
    public void testDefaultConstructor() {
        // Verify that a DTO is created properly with the default constructor
        assertNotNull(dto);
        assertNull(dto.getUsername());
        assertNull(dto.getPassword());
    }
    
    @Test
    public void testParameterizedConstructor() {
        // Create a DTO using the parameterized constructor
        AuthRequest paramDto = new AuthRequest(testUsername, testPassword);
        
        // Verify all values are set correctly
        assertEquals(testUsername, paramDto.getUsername());
        assertEquals(testPassword, paramDto.getPassword());
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
    public void testPasswordGetterSetter() {
        dto.setPassword(testPassword);
        assertEquals(testPassword, dto.getPassword());
        
        // Test with another value
        String newPassword = "newTestPass456";
        dto.setPassword(newPassword);
        assertEquals(newPassword, dto.getPassword());
    }
}
