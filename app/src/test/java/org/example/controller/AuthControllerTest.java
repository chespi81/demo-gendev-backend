package org.example.controller;

import org.example.dto.AuthRequest;
import org.example.dto.AuthResponse;
import org.example.model.User;
import org.example.service.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private User testUser;
    private String testToken;
    private AuthRequest authRequest;

    @Before
    public void setup() {
        // Setup test user
        testUser = new User();
        // Note: User class doesn't have setId method
        testUser.setUsername("testuser");
        testUser.setPassword("password"); // In real application, this would be hashed
        testUser.setOwnerId("user123");
        // Note: User class doesn't have email/firstName/lastName fields

        // Setup test token
        testToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyMTIzIiwibmFtZSI6InRlc3R1c2VyIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        // Setup auth request
        authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("password");
    }

    @Test
    public void testLogin_Success() {
        // Mock service behavior
        when(authService.authenticate("testuser", "password")).thenReturn(testUser);
        when(authService.generateToken(testUser)).thenReturn(testToken);

        // Execute the method to test
        ResponseEntity<AuthResponse> response = authController.login(authRequest);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testuser", response.getBody().getUsername());
        assertEquals("user123", response.getBody().getOwnerId());
        assertEquals(testToken, response.getBody().getToken());
        assertTrue(response.getBody().isAuthenticated());
    }

    @Test
    public void testLogin_Failure() {
        // Mock service behavior
        when(authService.authenticate("testuser", "password")).thenReturn(null);

        // Execute the method to test
        ResponseEntity<AuthResponse> response = authController.login(authRequest);

        // Verify the result
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isAuthenticated());
    }

    @Test
    public void testValidateToken_Valid() {
        // Mock service behavior
        when(authService.validateToken(testToken)).thenReturn("user123");

        // Execute the method to test
        ResponseEntity<Void> response = authController.validateToken(testToken);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testValidateToken_Invalid() {
        // Mock service behavior
        when(authService.validateToken(testToken)).thenReturn(null);

        // Execute the method to test
        ResponseEntity<Void> response = authController.validateToken(testToken);

        // Verify the result
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testLogout() {
        // Execute the method to test
        ResponseEntity<Void> response = authController.logout(testToken);

        // Verify the service was called
        verify(authService, times(1)).invalidateToken(testToken);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}