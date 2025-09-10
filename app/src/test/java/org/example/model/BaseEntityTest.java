package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase BaseEntity
 */
public class BaseEntityTest {
    
    // Implementación concreta de BaseEntity para pruebas
    private static class TestEntity extends BaseEntity {
        // Clase concreta para probar la clase abstracta
    }
    
    private TestEntity entity;
    
    @Before
    public void setUp() {
        entity = new TestEntity();
    }
    
    @Test
    public void testIdGetterSetter() {
        // Verificar que el ID es null por defecto
        assertNull(entity.getId());
        
        // Establecer y verificar un valor para ID
        Long expectedId = 1L;
        entity.setId(expectedId);
        assertEquals(expectedId, entity.getId());
        
        // Cambiar el valor y verificar nuevamente
        expectedId = 42L;
        entity.setId(expectedId);
        assertEquals(expectedId, entity.getId());
        
        // Verificar que se puede establecer a null
        entity.setId(null);
        assertNull(entity.getId());
    }
}
