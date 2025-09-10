package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Pruebas unitarias para la clase AccountTransaction
 */
public class AccountTransactionTest {
    
    private AccountTransaction accountTransaction;
    private Date testTransactionDate;
    private BigDecimal testAmount;
    private String testDescription;
    private String testType;
    private Account testAccount;
    
    @Before
    public void setUp() {
        testTransactionDate = new Date();
        testAmount = new BigDecimal("150.25");
        testDescription = "Test Deposit";
        testType = "CREDIT";
        
        // Crear una cuenta para pruebas
        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setAccountNumber("12345");
        testAccount.setBalance(new BigDecimal("1000.00"));
        
        // Crear una instancia con constructor por defecto
        accountTransaction = new AccountTransaction();
    }
    
    @Test
    public void testDefaultConstructor() {
        // Verificar que se crea correctamente con el constructor por defecto
        assertNotNull(accountTransaction);
        assertNull(accountTransaction.getTransactionDate());
        assertNull(accountTransaction.getAmount());
        assertNull(accountTransaction.getDescription());
        assertNull(accountTransaction.getType());
        assertNull(accountTransaction.getAccount());
    }
    
    @Test
    public void testParameterizedConstructor() {
        // Crear una instancia con el constructor parametrizado
        AccountTransaction paramTransaction = new AccountTransaction(testTransactionDate, testAmount, 
                                                                   testDescription, testType, testAccount);
        
        // Verificar que todos los valores se establecen correctamente
        assertEquals(testTransactionDate, paramTransaction.getTransactionDate());
        assertEquals(testAmount, paramTransaction.getAmount());
        assertEquals(testDescription, paramTransaction.getDescription());
        assertEquals(testType, paramTransaction.getType());
        assertEquals(testAccount, paramTransaction.getAccount());
    }
    
    @Test
    public void testAccountGetterSetter() {
        accountTransaction.setAccount(testAccount);
        assertEquals(testAccount, accountTransaction.getAccount());
        
        // Probar con otra cuenta
        Account newAccount = new Account();
        newAccount.setId(2L);
        newAccount.setAccountNumber("54321");
        accountTransaction.setAccount(newAccount);
        assertEquals(newAccount, accountTransaction.getAccount());
    }
    
    @Test
    public void testInheritedProperties() {
        // Establecer valores para las propiedades heredadas
        accountTransaction.setTransactionDate(testTransactionDate);
        accountTransaction.setAmount(testAmount);
        accountTransaction.setDescription(testDescription);
        accountTransaction.setType(testType);
        
        // Verificar que se establecieron correctamente
        assertEquals(testTransactionDate, accountTransaction.getTransactionDate());
        assertEquals(testAmount, accountTransaction.getAmount());
        assertEquals(testDescription, accountTransaction.getDescription());
        assertEquals(testType, accountTransaction.getType());
    }
    
    @Test
    public void testInheritance() {
        // Verificar que AccountTransaction hereda de BaseTransaction
        assertTrue(accountTransaction instanceof BaseTransaction);
        
        // Verificar que hereda indirectamente de BaseEntity
        assertTrue(accountTransaction instanceof BaseEntity);
        
        // Verificar que los métodos heredados funcionan
        Long testId = 123L;
        accountTransaction.setId(testId);
        assertEquals(testId, accountTransaction.getId());
    }
    
    @Test
    public void testAccountRelationship() {
        // Configurar la transacción
        accountTransaction.setAccount(testAccount);
        accountTransaction.setAmount(testAmount);
        accountTransaction.setType("CREDIT");
        
        // Verificar la relación bidireccional con Account
        testAccount.addTransaction(accountTransaction);
        
        // La transacción debería estar en la lista de transacciones de la cuenta
        assertTrue(testAccount.getTransactions().contains(accountTransaction));
        assertEquals(1, testAccount.getTransactions().size());
        
        // La cuenta en la transacción debería ser la misma
        assertEquals(testAccount, accountTransaction.getAccount());
    }
}
