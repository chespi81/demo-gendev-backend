package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Pruebas unitarias para la clase Message
 */
public class MessageTest {
    
    private Message message;
    private String testContent;
    
    @Before
    public void setUp() {
        testContent = "Test message content";
        
        // Crear una instancia con constructor por defecto
        message = new Message();
    }
    
    @Test
    public void testDefaultConstructor() {
        // Verificar que se crea correctamente con el constructor por defecto
        assertNotNull(message);
        assertNull(message.getId());
        assertNull(message.getContent());
        assertNotNull(message.getTimestamp());
        
        // El timestamp debería ser cercano al momento de creación
        LocalDateTime now = LocalDateTime.now();
        assertTrue(ChronoUnit.SECONDS.between(message.getTimestamp(), now) < 5);
    }
    
    @Test
    public void testParameterizedConstructor() {
        // Crear una instancia con el constructor parametrizado
        Message paramMessage = new Message(testContent);
        
        // Verificar que todos los valores se establecen correctamente
        assertNull(paramMessage.getId());
        assertEquals(testContent, paramMessage.getContent());
        assertNotNull(paramMessage.getTimestamp());
        
        // El timestamp debería ser cercano al momento de creación
        LocalDateTime now = LocalDateTime.now();
        assertTrue(ChronoUnit.SECONDS.between(paramMessage.getTimestamp(), now) < 5);
    }
    
    @Test
    public void testIdGetterSetter() {
        assertNull(message.getId());
        
        // Establecer y verificar un ID
        Long testId = 123L;
        message.setId(testId);
        assertEquals(testId, message.getId());
        
        // Cambiar el ID y verificar
        Long newId = 456L;
        message.setId(newId);
        assertEquals(newId, message.getId());
        
        // Establecer a null y verificar
        message.setId(null);
        assertNull(message.getId());
    }
    
    @Test
    public void testContentGetterSetter() {
        assertNull(message.getContent());
        
        // Establecer y verificar contenido
        message.setContent(testContent);
        assertEquals(testContent, message.getContent());
        
        // Cambiar el contenido y verificar
        String newContent = "Updated message content";
        message.setContent(newContent);
        assertEquals(newContent, message.getContent());
        
        // Establecer string vacío y verificar
        message.setContent("");
        assertEquals("", message.getContent());
        
        // Establecer a null y verificar
        message.setContent(null);
        assertNull(message.getContent());
    }
    
    @Test
    public void testTimestampGetterSetter() {
        // Por defecto no debería ser null
        assertNotNull(message.getTimestamp());
        
        // Establecer y verificar timestamp
        LocalDateTime testTimestamp = LocalDateTime.now().minusDays(1);
        message.setTimestamp(testTimestamp);
        assertEquals(testTimestamp, message.getTimestamp());
        
        // Cambiar el timestamp y verificar
        LocalDateTime newTimestamp = LocalDateTime.now().plusDays(1);
        message.setTimestamp(newTimestamp);
        assertEquals(newTimestamp, message.getTimestamp());
        
        // Establecer a null y verificar
        message.setTimestamp(null);
        assertNull(message.getTimestamp());
    }
    
    @Test
    public void testTimestampIsSetByDefaultConstructor() {
        // Crear varias instancias y verificar que el timestamp se establece automáticamente
        Message message1 = new Message();
        Message message2 = new Message();
        
        assertNotNull(message1.getTimestamp());
        assertNotNull(message2.getTimestamp());
        
        // Los timestamps deberían ser diferentes aunque estén cerca en el tiempo
        assertNotEquals(message1.getTimestamp(), message2.getTimestamp());
    }
    
    @Test
    public void testTimestampIsSetByParameterizedConstructor() {
        // Crear varias instancias y verificar que el timestamp se establece automáticamente
        Message message1 = new Message("Message 1");
        Message message2 = new Message("Message 2");
        
        assertNotNull(message1.getTimestamp());
        assertNotNull(message2.getTimestamp());
        
        // Los timestamps deberían ser diferentes aunque estén cerca en el tiempo
        assertNotEquals(message1.getTimestamp(), message2.getTimestamp());
    }
}
