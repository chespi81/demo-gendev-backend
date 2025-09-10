package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Pruebas unitarias para la clase Transaction
 */
public class TransactionTest {
    
    private Transaction transaction;
    private Date testTransactionDate;
    private BigDecimal testAmount;
    private String testDescription;
    private String testType;
    private CreditCard testCreditCard;
    
    @Before
    public void setUp() {
        testTransactionDate = new Date();
        testAmount = new BigDecimal("50.75");
        testDescription = "Test Purchase";
        testType = "CHARGE";
        testCreditCard = new CreditCard();
        testCreditCard.setId(1L);
        
        // Crear una instancia con constructor por defecto
        transaction = new Transaction();
    }
    
    @Test
    public void testDefaultConstructor() {
        // Verificar que se crea correctamente con el constructor por defecto
        assertNotNull(transaction);
        assertNull(transaction.getTransactionDate());
        assertNull(transaction.getAmount());
        assertNull(transaction.getDescription());
        assertNull(transaction.getType());
        assertNull(transaction.getCreditCard());
    }
    
    @Test
    public void testParameterizedConstructor() {
        // Crear una instancia con el constructor parametrizado
        Transaction paramTransaction = new Transaction(testTransactionDate, testAmount, 
                                                      testDescription, testType, testCreditCard);
        
        // Verificar que todos los valores se establecen correctamente
        assertEquals(testTransactionDate, paramTransaction.getTransactionDate());
        assertEquals(testAmount, paramTransaction.getAmount());
        assertEquals(testDescription, paramTransaction.getDescription());
        assertEquals(testType, paramTransaction.getType());
        assertEquals(testCreditCard, paramTransaction.getCreditCard());
    }
    
    @Test
    public void testCreditCardGetterSetter() {
        transaction.setCreditCard(testCreditCard);
        assertEquals(testCreditCard, transaction.getCreditCard());
        
        // Probar con otra tarjeta
        CreditCard newCreditCard = new CreditCard();
        newCreditCard.setId(2L);
        transaction.setCreditCard(newCreditCard);
        assertEquals(newCreditCard, transaction.getCreditCard());
    }
    
    @Test
    public void testInheritedProperties() {
        // Establecer valores para las propiedades heredadas
        transaction.setTransactionDate(testTransactionDate);
        transaction.setAmount(testAmount);
        transaction.setDescription(testDescription);
        transaction.setType(testType);
        
        // Verificar que se establecieron correctamente
        assertEquals(testTransactionDate, transaction.getTransactionDate());
        assertEquals(testAmount, transaction.getAmount());
        assertEquals(testDescription, transaction.getDescription());
        assertEquals(testType, transaction.getType());
    }
    
    @Test
    public void testInheritance() {
        // Verificar que Transaction hereda de BaseTransaction
        assertTrue(transaction instanceof BaseTransaction);
        
        // Verificar que hereda indirectamente de BaseEntity
        assertTrue(transaction instanceof BaseEntity);
        
        // Verificar que los métodos heredados funcionan
        Long testId = 123L;
        transaction.setId(testId);
        assertEquals(testId, transaction.getId());
    }
}
