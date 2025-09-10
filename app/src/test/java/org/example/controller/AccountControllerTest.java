package org.example.controller;

import org.example.dto.AccountTransactionDTO;
import org.example.model.Account;
import org.example.model.AccountTransaction;
import org.example.service.AccountService;
import org.example.service.AccountTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private AccountTransactionService accountTransactionService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testGetAccountsByOwnerId_Success() {
        // Prepare test data
        String ownerId = "user123";
        List<Account> accounts = new ArrayList<>();
        
        Account account1 = new Account();
        account1.setId(1L);
        account1.setOwnerId(ownerId);
        account1.setBalance(new BigDecimal("1000.00"));
        account1.setAccountType("CHECKING");
        account1.setAccountNumber("ACC123");
        account1.setCreationDate(LocalDateTime.now());
        
        Account account2 = new Account();
        account2.setId(2L);
        account2.setOwnerId(ownerId);
        account2.setBalance(new BigDecimal("5000.00"));
        account2.setAccountType("SAVINGS");
        account2.setAccountNumber("ACC456");
        account2.setCreationDate(LocalDateTime.now());
        
        accounts.add(account1);
        accounts.add(account2);

        // Mock service behavior
        when(accountService.getAccountsByOwnerId(ownerId)).thenReturn(accounts);

        // Execute the method to test
        ResponseEntity<List<Account>> responseEntity = accountController.getAccountsByOwnerId(ownerId);

        // Verify the result
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(account1.getId(), responseEntity.getBody().get(0).getId());
        assertEquals(account2.getId(), responseEntity.getBody().get(1).getId());
    }

    @Test
    public void testGetAccountsByOwnerId_NotFound() {
        // Prepare test data
        String ownerId = "nonexistent";
        List<Account> emptyList = new ArrayList<>();

        // Mock service behavior
        when(accountService.getAccountsByOwnerId(ownerId)).thenReturn(emptyList);

        // Execute the method to test
        ResponseEntity<List<Account>> responseEntity = accountController.getAccountsByOwnerId(ownerId);

        // Verify the result
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @Test
    public void testGetTransactionsByAccountId_Success() {
        // Prepare test data
        Long accountId = 1L;
        List<AccountTransactionDTO> transactions = new ArrayList<>();
        
        AccountTransactionDTO transaction1 = new AccountTransactionDTO();
        transaction1.setId(1L);
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setDescription("Purchase");
        transaction1.setType("CHARGE");
        transaction1.setDate(new Date());
        
        AccountTransactionDTO transaction2 = new AccountTransactionDTO();
        transaction2.setId(2L);
        transaction2.setAmount(new BigDecimal("200.00"));
        transaction2.setDescription("Deposit");
        transaction2.setType("CREDIT");
        transaction2.setDate(new Date());
        
        transactions.add(transaction1);
        transactions.add(transaction2);

        // Mock service behavior
        when(accountTransactionService.getTransactionDTOsByAccountId(accountId)).thenReturn(transactions);

        // Execute the method to test
        ResponseEntity<List<AccountTransactionDTO>> responseEntity = accountController.getTransactionsByAccountId(accountId);

        // Verify the result
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(transaction1.getId(), responseEntity.getBody().get(0).getId());
        assertEquals(transaction2.getId(), responseEntity.getBody().get(1).getId());
    }

    @Test
    public void testGetTransactionsByAccountId_NotFound() {
        // Prepare test data
        Long accountId = 999L;
        List<AccountTransactionDTO> emptyList = new ArrayList<>();

        // Mock service behavior
        when(accountTransactionService.getTransactionDTOsByAccountId(accountId)).thenReturn(emptyList);

        // Execute the method to test
        ResponseEntity<List<AccountTransactionDTO>> responseEntity = accountController.getTransactionsByAccountId(accountId);

        // Verify the result
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @Test
    public void testGetLastTransactionsByAccountId_Success() {
        // Prepare test data
        Long accountId = 1L;
        int count = 5;
        List<AccountTransactionDTO> transactions = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            AccountTransactionDTO transaction = new AccountTransactionDTO();
            transaction.setId((long) (i + 1));
            transaction.setAmount(new BigDecimal("100.00").multiply(new BigDecimal(i + 1)));
            transaction.setDescription("Transaction " + (i + 1));
            transaction.setType(i % 2 == 0 ? "CHARGE" : "CREDIT");
            transaction.setDate(new Date());
            transactions.add(transaction);
        }

        // Mock service behavior
        when(accountTransactionService.getLastTransactionDTOsByAccountId(accountId, count)).thenReturn(transactions);

        // Execute the method to test
        ResponseEntity<List<AccountTransactionDTO>> responseEntity = accountController.getLastTransactionsByAccountId(accountId, count);

        // Verify the result
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(count, responseEntity.getBody().size());
    }

    @Test
    public void testGetTransactionsByAccountIdAndType_Success() {
        // Prepare test data
        Long accountId = 1L;
        String type = "CHARGE";
        List<AccountTransactionDTO> transactions = new ArrayList<>();
        
        AccountTransactionDTO transaction1 = new AccountTransactionDTO();
        transaction1.setId(1L);
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setDescription("Purchase 1");
        transaction1.setType(type);
        transaction1.setDate(new Date());
        
        AccountTransactionDTO transaction2 = new AccountTransactionDTO();
        transaction2.setId(3L);
        transaction2.setAmount(new BigDecimal("150.00"));
        transaction2.setDescription("Purchase 2");
        transaction2.setType(type);
        transaction2.setDate(new Date());
        
        transactions.add(transaction1);
        transactions.add(transaction2);

        // Mock service behavior
        when(accountTransactionService.getTransactionDTOsByAccountIdAndType(accountId, type)).thenReturn(transactions);

        // Execute the method to test
        ResponseEntity<List<AccountTransactionDTO>> responseEntity = accountController.getTransactionsByAccountIdAndType(accountId, type);

        // Verify the result
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(type, responseEntity.getBody().get(0).getType());
        assertEquals(type, responseEntity.getBody().get(1).getType());
    }

    @Test
    public void testAddTransaction_Success() {
        // Prepare test data
        Long accountId = 1L;
        AccountTransaction transaction = new AccountTransaction();
        transaction.setAmount(new BigDecimal("250.00"));
        transaction.setDescription("New Transaction");
        transaction.setType("CREDIT");
        transaction.setTransactionDate(new Date());

        // Mock service behavior
        when(accountTransactionService.saveTransaction(any(AccountTransaction.class), eq(accountId)))
            .thenReturn(Optional.of(transaction));

        // Execute the method to test
        ResponseEntity<AccountTransaction> responseEntity = accountController.addTransaction(accountId, transaction);

        // Verify the result
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(transaction, responseEntity.getBody());
    }

    @Test
    public void testAddTransaction_AccountNotFound() {
        // Prepare test data
        Long accountId = 999L;
        AccountTransaction transaction = new AccountTransaction();
        transaction.setAmount(new BigDecimal("250.00"));
        transaction.setDescription("New Transaction");
        transaction.setType("CREDIT");
        transaction.setTransactionDate(new Date());

        // Mock service behavior
        when(accountTransactionService.saveTransaction(any(AccountTransaction.class), eq(accountId)))
            .thenReturn(Optional.empty());

        // Execute the method to test
        ResponseEntity<AccountTransaction> responseEntity = accountController.addTransaction(accountId, transaction);

        // Verify the result
        assertEquals(404, responseEntity.getStatusCode().value());
    }
}