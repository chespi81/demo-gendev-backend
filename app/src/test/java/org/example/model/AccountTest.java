package org.example.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Pruebas unitarias para la clase Account
 */
public class AccountTest {
    
    private Account account;
    private String testAccountNumber;
    private String testAccountType;
    private BigDecimal testBalance;
    private String testOwnerId;
    private LocalDateTime testCreationDate;
    
    @Before
    public void setUp() {
        testAccountNumber = "123456789";
        testAccountType = "Current";
        testBalance = new BigDecimal("1000.00");
        testOwnerId = "12345";
        testCreationDate = LocalDateTime.now();
        
        // Crear una instancia con constructor por defecto
        account = new Account();
    }
    
    @Test
    public void testDefaultConstructor() {
        // Verificar que se crea correctamente con el constructor por defecto
        assertNotNull(account);
        assertNull(account.getAccountNumber());
        assertNull(account.getAccountType());
        assertNull(account.getBalance());
        assertNull(account.getOwnerId());
        assertNull(account.getCreationDate());
        assertNotNull(account.getTransactions());
        assertTrue(account.getTransactions().isEmpty());
    }
    
    @Test
    public void testParameterizedConstructor() {
        // Crear una instancia con el constructor parametrizado
        Account paramAccount = new Account(testAccountNumber, testAccountType, testBalance, testOwnerId, testCreationDate);
        
        // Verificar que todos los valores se establecen correctamente
        assertEquals(testAccountNumber, paramAccount.getAccountNumber());
        assertEquals(testAccountType, paramAccount.getAccountType());
        assertEquals(testBalance, paramAccount.getBalance());
        assertEquals(testOwnerId, paramAccount.getOwnerId());
        assertEquals(testCreationDate, paramAccount.getCreationDate());
        assertNotNull(paramAccount.getTransactions());
        assertTrue(paramAccount.getTransactions().isEmpty());
    }
    
    @Test
    public void testAccountNumberGetterSetter() {
        account.setAccountNumber(testAccountNumber);
        assertEquals(testAccountNumber, account.getAccountNumber());
        
        // Probar con otro valor
        String newAccountNumber = "987654321";
        account.setAccountNumber(newAccountNumber);
        assertEquals(newAccountNumber, account.getAccountNumber());
    }
    
    @Test
    public void testAccountTypeGetterSetter() {
        account.setAccountType(testAccountType);
        assertEquals(testAccountType, account.getAccountType());
        
        // Probar con otro valor
        String newAccountType = "Demand";
        account.setAccountType(newAccountType);
        assertEquals(newAccountType, account.getAccountType());
    }
    
    @Test
    public void testBalanceGetterSetter() {
        account.setBalance(testBalance);
        assertEquals(testBalance, account.getBalance());
        
        // Probar con otro valor
        BigDecimal newBalance = new BigDecimal("2000.50");
        account.setBalance(newBalance);
        assertEquals(newBalance, account.getBalance());
    }
    
    @Test
    public void testOwnerIdGetterSetter() {
        account.setOwnerId(testOwnerId);
        assertEquals(testOwnerId, account.getOwnerId());
        
        // Probar con otro valor
        String newOwnerId = "67890";
        account.setOwnerId(newOwnerId);
        assertEquals(newOwnerId, account.getOwnerId());
    }
    
    @Test
    public void testCreationDateGetterSetter() {
        account.setCreationDate(testCreationDate);
        assertEquals(testCreationDate, account.getCreationDate());
        
        // Probar con otro valor
        LocalDateTime newCreationDate = LocalDateTime.now().plusDays(1);
        account.setCreationDate(newCreationDate);
        assertEquals(newCreationDate, account.getCreationDate());
    }
    
    @Test
    public void testTransactionsGetterSetter() {
        // Crear una lista de transacciones
        List<AccountTransaction> transactions = new ArrayList<>();
        AccountTransaction transaction1 = new AccountTransaction();
        AccountTransaction transaction2 = new AccountTransaction();
        transactions.add(transaction1);
        transactions.add(transaction2);
        
        // Establecer la lista
        account.setTransactions(transactions);
        
        // Verificar que se estableció correctamente
        assertEquals(transactions, account.getTransactions());
        assertEquals(2, account.getTransactions().size());
    }
    
    @Test
    public void testAddTransaction() {
        // Crear una transacción
        AccountTransaction transaction = new AccountTransaction();
        
        // Añadir la transacción a la cuenta
        account.addTransaction(transaction);
        
        // Verificar que se añadió correctamente
        assertEquals(1, account.getTransactions().size());
        assertTrue(account.getTransactions().contains(transaction));
        assertEquals(account, transaction.getAccount());
    }
    
    @Test
    public void testRemoveTransaction() {
        // Crear una transacción
        AccountTransaction transaction = new AccountTransaction();
        
        // Añadir la transacción a la cuenta
        account.addTransaction(transaction);
        assertEquals(1, account.getTransactions().size());
        
        // Eliminar la transacción
        account.removeTransaction(transaction);
        
        // Verificar que se eliminó correctamente
        assertEquals(0, account.getTransactions().size());
        assertFalse(account.getTransactions().contains(transaction));
        assertNull(transaction.getAccount());
    }
    
    @Test
    public void testInheritance() {
        // Verificar que Account hereda de BaseEntity
        assertTrue(account instanceof BaseEntity);
        
        // Verificar que los métodos heredados funcionan
        Long testId = 123L;
        account.setId(testId);
        assertEquals(testId, account.getId());
    }
}
