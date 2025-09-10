package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Pruebas unitarias para la clase BaseTransaction
 */
public class BaseTransactionTest {
    
    // Implementación concreta de BaseTransaction para pruebas
    private static class TestTransaction extends BaseTransaction {
        // Clase concreta para probar la clase abstracta
    }
    
    private TestTransaction transaction;
    private Date testDate;
    private BigDecimal testAmount;
    private String testDescription;
    private String testType;
    
    @Before
    public void setUp() {
        transaction = new TestTransaction();
        testDate = new Date();
        testAmount = new BigDecimal("100.50");
        testDescription = "Test Transaction";
        testType = "CREDIT";
    }
    
    @Test
    public void testTransactionDateGetterSetter() {
        // Verificar que es null por defecto
        assertNull(transaction.getTransactionDate());
        
        // Establecer y verificar un valor para transactionDate
        transaction.setTransactionDate(testDate);
        assertEquals(testDate, transaction.getTransactionDate());
    }
    
    @Test
    public void testAmountGetterSetter() {
        // Verificar que es null por defecto
        assertNull(transaction.getAmount());
        
        // Establecer y verificar un valor para amount
        transaction.setAmount(testAmount);
        assertEquals(testAmount, transaction.getAmount());
        
        // Verificar precisión decimal
        BigDecimal precisionAmount = new BigDecimal("123456789.99");
        transaction.setAmount(precisionAmount);
        assertEquals(precisionAmount, transaction.getAmount());
    }
    
    @Test
    public void testDescriptionGetterSetter() {
        // Verificar que es null por defecto
        assertNull(transaction.getDescription());
        
        // Establecer y verificar un valor para description
        transaction.setDescription(testDescription);
        assertEquals(testDescription, transaction.getDescription());
        
        // Verificar con string vacío
        transaction.setDescription("");
        assertEquals("", transaction.getDescription());
    }
    
    @Test
    public void testTypeGetterSetter() {
        // Verificar que es null por defecto
        assertNull(transaction.getType());
        
        // Establecer y verificar un valor para type (CREDIT)
        transaction.setType(testType);
        assertEquals(testType, transaction.getType());
        
        // Verificar con otro valor (CHARGE)
        String newType = "CHARGE";
        transaction.setType(newType);
        assertEquals(newType, transaction.getType());
    }
    
    @Test
    public void testInheritance() {
        // Verificar que BaseTransaction hereda de BaseEntity
        assertTrue(transaction instanceof BaseEntity);
        
        // Verificar que los métodos heredados funcionan
        Long testId = 123L;
        transaction.setId(testId);
        assertEquals(testId, transaction.getId());
    }
}
