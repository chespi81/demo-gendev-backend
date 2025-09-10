package org.example.service;

import org.example.model.Account;
import org.example.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account testAccount;
    private final Long testId = 1L;
    private final String testOwnerId = "TEST123";
    private final String testAccountType = "SAVINGS";
    private final String testAccountNumber = "12345678";
    private final BigDecimal testBalance = new BigDecimal("1000.50");
    private final LocalDateTime testCreationDate = LocalDateTime.now();

    @Before
    public void setUp() {
        testAccount = new Account();
        testAccount.setId(testId);
        testAccount.setOwnerId(testOwnerId);
        testAccount.setAccountType(testAccountType);
        testAccount.setAccountNumber(testAccountNumber);
        testAccount.setBalance(testBalance);
        testAccount.setCreationDate(testCreationDate);
    }

    @Test
    public void testGetAccountsByOwnerId() {
        // Arrange
        List<Account> expectedAccounts = Arrays.asList(testAccount);
        when(accountRepository.findByOwnerId(testOwnerId)).thenReturn(expectedAccounts);

        // Act
        List<Account> resultAccounts = accountService.getAccountsByOwnerId(testOwnerId);

        // Assert
        assertEquals(expectedAccounts, resultAccounts);
        verify(accountRepository).findByOwnerId(testOwnerId);
    }

    @Test
    public void testGetAccountById_WhenAccountExists() {
        // Arrange
        when(accountRepository.findById(testId)).thenReturn(Optional.of(testAccount));

        // Act
        Optional<Account> result = accountService.getAccountById(testId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testAccount, result.get());
        verify(accountRepository).findById(testId);
    }

    @Test
    public void testGetAccountById_WhenAccountDoesNotExist() {
        // Arrange
        when(accountRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        Optional<Account> result = accountService.getAccountById(testId);

        // Assert
        assertFalse(result.isPresent());
        verify(accountRepository).findById(testId);
    }

    @Test
    public void testSaveAccount() {
        // Arrange
        when(accountRepository.save(testAccount)).thenReturn(testAccount);

        // Act
        Account result = accountService.saveAccount(testAccount);

        // Assert
        assertEquals(testAccount, result);
        verify(accountRepository).save(testAccount);
    }

    @Test
    public void testUpdateAccount_WhenAccountExists() {
        // Arrange
        Account updatedAccount = new Account();
        updatedAccount.setOwnerId(testOwnerId);
        updatedAccount.setAccountType("CHECKING");
        updatedAccount.setAccountNumber(testAccountNumber);
        updatedAccount.setBalance(new BigDecimal("2000.00"));
        updatedAccount.setCreationDate(testCreationDate);

        when(accountRepository.existsById(testId)).thenReturn(true);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            assertEquals(testId, savedAccount.getId());
            return savedAccount;
        });

        // Act
        Optional<Account> result = accountService.updateAccount(testId, updatedAccount);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testId, result.get().getId());
        assertEquals("CHECKING", result.get().getAccountType());
        assertEquals(new BigDecimal("2000.00"), result.get().getBalance());
        verify(accountRepository).existsById(testId);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    public void testUpdateAccount_WhenAccountDoesNotExist() {
        // Arrange
        Account updatedAccount = new Account();
        when(accountRepository.existsById(testId)).thenReturn(false);

        // Act
        Optional<Account> result = accountService.updateAccount(testId, updatedAccount);

        // Assert
        assertFalse(result.isPresent());
        verify(accountRepository).existsById(testId);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    public void testDeleteAccount_WhenAccountExists() {
        // Arrange
        when(accountRepository.existsById(testId)).thenReturn(true);
        doNothing().when(accountRepository).deleteById(testId);

        // Act
        boolean result = accountService.deleteAccount(testId);

        // Assert
        assertTrue(result);
        verify(accountRepository).existsById(testId);
        verify(accountRepository).deleteById(testId);
    }

    @Test
    public void testDeleteAccount_WhenAccountDoesNotExist() {
        // Arrange
        when(accountRepository.existsById(testId)).thenReturn(false);

        // Act
        boolean result = accountService.deleteAccount(testId);

        // Assert
        assertFalse(result);
        verify(accountRepository).existsById(testId);
        verify(accountRepository, never()).deleteById(any());
    }
}