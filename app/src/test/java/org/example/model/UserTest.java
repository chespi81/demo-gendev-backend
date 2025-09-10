package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase User
 */
public class UserTest {
    
    private User user;
    private String testUsername;
    private String testPassword;
    private String testOwnerId;
    
    @Before
    public void setUp() {
        testUsername = "testuser";
        testPassword = "password123";
        testOwnerId = "12345";
        
        // Crear una instancia con constructor por defecto
        user = new User();
    }
    
    @Test
    public void testDefaultConstructor() {
        // Verificar que se crea correctamente con el constructor por defecto
        assertNotNull(user);
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getOwnerId());
    }
    
    @Test
    public void testParameterizedConstructor() {
        // Crear una instancia con el constructor parametrizado
        User paramUser = new User(testUsername, testPassword, testOwnerId);
        
        // Verificar que todos los valores se establecen correctamente
        assertEquals(testUsername, paramUser.getUsername());
        assertEquals(testPassword, paramUser.getPassword());
        assertEquals(testOwnerId, paramUser.getOwnerId());
    }
    
    @Test
    public void testUsernameGetterSetter() {
        user.setUsername(testUsername);
        assertEquals(testUsername, user.getUsername());
        
        // Probar con otro valor
        String newUsername = "anotheruser";
        user.setUsername(newUsername);
        assertEquals(newUsername, user.getUsername());
        
        // Probar con null
        user.setUsername(null);
        assertNull(user.getUsername());
    }
    
    @Test
    public void testPasswordGetterSetter() {
        user.setPassword(testPassword);
        assertEquals(testPassword, user.getPassword());
        
        // Probar con otro valor
        String newPassword = "newpassword456";
        user.setPassword(newPassword);
        assertEquals(newPassword, user.getPassword());
        
        // Probar con null
        user.setPassword(null);
        assertNull(user.getPassword());
    }
    
    @Test
    public void testOwnerIdGetterSetter() {
        user.setOwnerId(testOwnerId);
        assertEquals(testOwnerId, user.getOwnerId());
        
        // Probar con otro valor
        String newOwnerId = "67890";
        user.setOwnerId(newOwnerId);
        assertEquals(newOwnerId, user.getOwnerId());
        
        // Probar con null
        user.setOwnerId(null);
        assertNull(user.getOwnerId());
    }
    
    @Test
    public void testUserEquality() {
        // Crear dos usuarios con los mismos valores
        User user1 = new User(testUsername, testPassword, testOwnerId);
        User user2 = new User(testUsername, testPassword, testOwnerId);
        
        // No son el mismo objeto
        assertNotSame(user1, user2);
        
        // La clase User no redefine equals(), por lo que se compara por referencia
        assertNotEquals(user1, user2);
    }
}
