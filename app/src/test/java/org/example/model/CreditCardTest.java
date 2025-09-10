package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Pruebas unitarias para la clase CreditCard
 */
public class CreditCardTest {
    
    private CreditCard creditCard;
    private String testLastFourDigits;
    private String testCardName;
    private String testValidUntil;
    private String testCardType;
    private boolean testStatus;
    private BigDecimal testLimit;
    private BigDecimal testBalance;
    private String testOwnerId;
    
    @Before
    public void setUp() {
        testLastFourDigits = "1234";
        testCardName = "Test User";
        testValidUntil = "12/25";
        testCardType = "Credit";
        testStatus = true;
        testLimit = new BigDecimal("5000.00");
        testBalance = new BigDecimal("1000.00");
        testOwnerId = "12345";
        
        // Crear una instancia con constructor por defecto
        creditCard = new CreditCard();
    }
    
    @Test
    public void testDefaultConstructor() {
        // Verificar que se crea correctamente con el constructor por defecto
        assertNotNull(creditCard);
        assertNull(creditCard.getLastFourDigits());
        assertNull(creditCard.getCardName());
        assertNull(creditCard.getValidUntil());
        assertNull(creditCard.getCardType());
        assertFalse(creditCard.isStatus()); // Por defecto debería ser false
        assertNull(creditCard.getLimit());
        assertNull(creditCard.getBalance());
        assertNull(creditCard.getOwnerId());
        assertNotNull(creditCard.getTransactions());
        assertTrue(creditCard.getTransactions().isEmpty());
    }
    
    @Test
    public void testParameterizedConstructor() {
        // Crear una instancia con el constructor parametrizado
        CreditCard paramCard = new CreditCard(testLastFourDigits, testCardName, testValidUntil,
                                             testCardType, testStatus, testLimit, testBalance, testOwnerId);
        
        // Verificar que todos los valores se establecen correctamente
        assertEquals(testLastFourDigits, paramCard.getLastFourDigits());
        assertEquals(testCardName, paramCard.getCardName());
        assertEquals(testValidUntil, paramCard.getValidUntil());
        assertEquals(testCardType, paramCard.getCardType());
        assertEquals(testStatus, paramCard.isStatus());
        assertEquals(testLimit, paramCard.getLimit());
        assertEquals(testBalance, paramCard.getBalance());
        assertEquals(testOwnerId, paramCard.getOwnerId());
        assertNotNull(paramCard.getTransactions());
        assertTrue(paramCard.getTransactions().isEmpty());
    }
    
    @Test
    public void testLastFourDigitsGetterSetter() {
        creditCard.setLastFourDigits(testLastFourDigits);
        assertEquals(testLastFourDigits, creditCard.getLastFourDigits());
        
        // Probar con otro valor
        String newLastFourDigits = "9876";
        creditCard.setLastFourDigits(newLastFourDigits);
        assertEquals(newLastFourDigits, creditCard.getLastFourDigits());
    }
    
    @Test
    public void testCardNameGetterSetter() {
        creditCard.setCardName(testCardName);
        assertEquals(testCardName, creditCard.getCardName());
        
        // Probar con otro valor
        String newCardName = "Another User";
        creditCard.setCardName(newCardName);
        assertEquals(newCardName, creditCard.getCardName());
    }
    
    @Test
    public void testValidUntilGetterSetter() {
        creditCard.setValidUntil(testValidUntil);
        assertEquals(testValidUntil, creditCard.getValidUntil());
        
        // Probar con otro valor
        String newValidUntil = "01/26";
        creditCard.setValidUntil(newValidUntil);
        assertEquals(newValidUntil, creditCard.getValidUntil());
    }
    
    @Test
    public void testCardTypeGetterSetter() {
        creditCard.setCardType(testCardType);
        assertEquals(testCardType, creditCard.getCardType());
        
        // Probar con otro valor
        String newCardType = "Debit";
        creditCard.setCardType(newCardType);
        assertEquals(newCardType, creditCard.getCardType());
    }
    
    @Test
    public void testStatusGetterSetter() {
        creditCard.setStatus(testStatus);
        assertEquals(testStatus, creditCard.isStatus());
        
        // Probar con el valor opuesto
        boolean newStatus = !testStatus;
        creditCard.setStatus(newStatus);
        assertEquals(newStatus, creditCard.isStatus());
    }
    
    @Test
    public void testLimitGetterSetter() {
        creditCard.setLimit(testLimit);
        assertEquals(testLimit, creditCard.getLimit());
        
        // Probar con otro valor
        BigDecimal newLimit = new BigDecimal("10000.00");
        creditCard.setLimit(newLimit);
        assertEquals(newLimit, creditCard.getLimit());
    }
    
    @Test
    public void testBalanceGetterSetter() {
        creditCard.setBalance(testBalance);
        assertEquals(testBalance, creditCard.getBalance());
        
        // Probar con otro valor
        BigDecimal newBalance = new BigDecimal("2500.50");
        creditCard.setBalance(newBalance);
        assertEquals(newBalance, creditCard.getBalance());
    }
    
    @Test
    public void testOwnerIdGetterSetter() {
        creditCard.setOwnerId(testOwnerId);
        assertEquals(testOwnerId, creditCard.getOwnerId());
        
        // Probar con otro valor
        String newOwnerId = "67890";
        creditCard.setOwnerId(newOwnerId);
        assertEquals(newOwnerId, creditCard.getOwnerId());
    }
    
    @Test
    public void testTransactionsGetterSetter() {
        // Crear una lista de transacciones
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        transactions.add(transaction1);
        transactions.add(transaction2);
        
        // Establecer la lista
        creditCard.setTransactions(transactions);
        
        // Verificar que se estableció correctamente
        assertEquals(transactions, creditCard.getTransactions());
        assertEquals(2, creditCard.getTransactions().size());
    }
    
    @Test
    public void testAddTransaction() {
        // Crear una transacción
        Transaction transaction = new Transaction();
        
        // Añadir la transacción a la tarjeta
        creditCard.addTransaction(transaction);
        
        // Verificar que se añadió correctamente
        assertEquals(1, creditCard.getTransactions().size());
        assertTrue(creditCard.getTransactions().contains(transaction));
        assertEquals(creditCard, transaction.getCreditCard());
    }
    
    @Test
    public void testRemoveTransaction() {
        // Crear una transacción
        Transaction transaction = new Transaction();
        
        // Añadir la transacción a la tarjeta
        creditCard.addTransaction(transaction);
        assertEquals(1, creditCard.getTransactions().size());
        
        // Eliminar la transacción
        creditCard.removeTransaction(transaction);
        
        // Verificar que se eliminó correctamente
        assertEquals(0, creditCard.getTransactions().size());
        assertFalse(creditCard.getTransactions().contains(transaction));
        assertNull(transaction.getCreditCard());
    }
    
    @Test
    public void testInheritance() {
        // Verificar que CreditCard hereda de BaseEntity
        assertTrue(creditCard instanceof BaseEntity);
        
        // Verificar que los métodos heredados funcionan
        Long testId = 123L;
        creditCard.setId(testId);
        assertEquals(testId, creditCard.getId());
    }
}
