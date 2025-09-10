package org.example.controller;

import org.example.dto.TransactionDTO;
import org.example.model.Transaction;
import org.example.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction1;
    private Transaction transaction2;
    private List<Transaction> transactionList;
    private TransactionDTO transactionDTO1;
    private TransactionDTO transactionDTO2;
    private List<TransactionDTO> transactionDTOList;
    private SimpleDateFormat dateFormat;

    @Before
    public void setup() {
        // Setup date formatter for consistent date handling
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Setup test transactions
        transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(new BigDecimal("100.50"));
        transaction1.setDescription("Purchase at Store");
        transaction1.setType("CHARGE");
        try {
            transaction1.setTransactionDate(dateFormat.parse("2023-01-15"));
        } catch (Exception e) {
            transaction1.setTransactionDate(new Date());
        }

        transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(new BigDecimal("50.25"));
        transaction2.setDescription("Online Purchase");
        transaction2.setType("CHARGE");
        try {
            transaction2.setTransactionDate(dateFormat.parse("2023-01-20"));
        } catch (Exception e) {
            transaction2.setTransactionDate(new Date());
        }

        transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);

        // Setup test transaction DTOs
        transactionDTO1 = new TransactionDTO();
        transactionDTO1.setId(1L);
        transactionDTO1.setAmount(new BigDecimal("100.50"));
        transactionDTO1.setDescription("Purchase at Store");
        transactionDTO1.setType("CHARGE");
        try {
            transactionDTO1.setDate(dateFormat.parse("2023-01-15"));
        } catch (Exception e) {
            transactionDTO1.setDate(new Date());
        }

        transactionDTO2 = new TransactionDTO();
        transactionDTO2.setId(2L);
        transactionDTO2.setAmount(new BigDecimal("50.25"));
        transactionDTO2.setDescription("Online Purchase");
        transactionDTO2.setType("CHARGE");
        try {
            transactionDTO2.setDate(dateFormat.parse("2023-01-20"));
        } catch (Exception e) {
            transactionDTO2.setDate(new Date());
        }

        transactionDTOList = new ArrayList<>();
        transactionDTOList.add(transactionDTO1);
        transactionDTOList.add(transactionDTO2);
    }

    @Test
    public void testGetTransactionsByCreditCardId_Success() {
        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardId(1L)).thenReturn(transactionList);

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByCreditCardId(1L);

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(transaction1.getId(), response.getBody().get(0).getId());
        assertEquals(transaction2.getId(), response.getBody().get(1).getId());
    }

    @Test
    public void testGetTransactionsByCreditCardId_NotFound() {
        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardId(999L)).thenReturn(new ArrayList<>());

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByCreditCardId(999L);

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testGetTransactionsByClientId_Success() {
        // Mock service behavior
        when(transactionService.getTransactionsByClientId("user123")).thenReturn(transactionDTOList);

        // Execute the method to test
        ResponseEntity<List<TransactionDTO>> response = transactionController.getTransactionsByClientId("user123");

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(transactionDTO1.getId(), response.getBody().get(0).getId());
        assertEquals(transactionDTO2.getId(), response.getBody().get(1).getId());
    }

    @Test
    public void testGetTransactionsByClientId_NotFound() {
        // Mock service behavior
        when(transactionService.getTransactionsByClientId("nonexistent")).thenReturn(new ArrayList<>());

        // Execute the method to test
        ResponseEntity<List<TransactionDTO>> response = transactionController.getTransactionsByClientId("nonexistent");

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testGetTransactionsByDateRange_Success() throws Exception {
        // Setup dates
        Date startDate = dateFormat.parse("2023-01-01");
        Date endDate = dateFormat.parse("2023-01-31");

        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardIdAndDateRange(1L, startDate, endDate))
                .thenReturn(transactionList);

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByDateRange(
                1L, startDate, endDate);

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetTransactionsByDateRange_NotFound() throws Exception {
        // Setup dates
        Date startDate = dateFormat.parse("2022-01-01");
        Date endDate = dateFormat.parse("2022-01-31");

        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardIdAndDateRange(1L, startDate, endDate))
                .thenReturn(new ArrayList<>());

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByDateRange(
                1L, startDate, endDate);

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testGetClientTransactionsByDateRange_Success() throws Exception {
        // Setup dates
        Date startDate = dateFormat.parse("2023-01-01");
        Date endDate = dateFormat.parse("2023-01-31");

        // Mock service behavior
        when(transactionService.getTransactionDTOsByCreditCardIdAndDateRange(1L, startDate, endDate))
                .thenReturn(transactionDTOList);

        // Execute the method to test
        ResponseEntity<List<TransactionDTO>> response = transactionController.getClientTransactionsByDateRange(
                "user123", 1L, startDate, endDate);

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetClientTransactionsByDateRange_NotFound() throws Exception {
        // Setup dates
        Date startDate = dateFormat.parse("2022-01-01");
        Date endDate = dateFormat.parse("2022-01-31");

        // Mock service behavior
        when(transactionService.getTransactionDTOsByCreditCardIdAndDateRange(1L, startDate, endDate))
                .thenReturn(new ArrayList<>());

        // Execute the method to test
        ResponseEntity<List<TransactionDTO>> response = transactionController.getClientTransactionsByDateRange(
                "user123", 1L, startDate, endDate);

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testGetTransactionsByType_Success() {
        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardIdAndType(1L, "CHARGE"))
                .thenReturn(transactionList);

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByType(
                1L, "CHARGE");

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("CHARGE", response.getBody().get(0).getType());
        assertEquals("CHARGE", response.getBody().get(1).getType());
    }

    @Test
    public void testGetTransactionsByType_NotFound() {
        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardIdAndType(1L, "CREDIT"))
                .thenReturn(new ArrayList<>());

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByType(
                1L, "CREDIT");

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testGetClientTransactionsByType_Success() {
        // Mock service behavior
        when(transactionService.getTransactionDTOsByCreditCardIdAndType(1L, "CHARGE"))
                .thenReturn(transactionDTOList);

        // Execute the method to test
        ResponseEntity<List<TransactionDTO>> response = transactionController.getClientTransactionsByType(
                "user123", 1L, "CHARGE");

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("CHARGE", response.getBody().get(0).getType());
        assertEquals("CHARGE", response.getBody().get(1).getType());
    }

    @Test
    public void testGetClientTransactionsByType_NotFound() {
        // Mock service behavior
        when(transactionService.getTransactionDTOsByCreditCardIdAndType(1L, "CREDIT"))
                .thenReturn(new ArrayList<>());

        // Execute the method to test
        ResponseEntity<List<TransactionDTO>> response = transactionController.getClientTransactionsByType(
                "user123", 1L, "CREDIT");

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testGetTransactionById_Success() {
        // Mock service behavior
        when(transactionService.getTransactionById(1L)).thenReturn(Optional.of(transaction1));

        // Execute the method to test
        ResponseEntity<Transaction> response = transactionController.getTransactionById(1L);

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(transaction1.getId(), response.getBody().getId());
        assertEquals(transaction1.getDescription(), response.getBody().getDescription());
    }

    @Test
    public void testGetTransactionById_NotFound() {
        // Mock service behavior
        when(transactionService.getTransactionById(999L)).thenReturn(Optional.empty());

        // Execute the method to test
        ResponseEntity<Transaction> response = transactionController.getTransactionById(999L);

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testCreateTransaction_Success() {
        // Prepare a new transaction
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(new BigDecimal("75.00"));
        newTransaction.setDescription("New Purchase");
        newTransaction.setType("CHARGE");
        newTransaction.setTransactionDate(new Date());

        // Mock service behavior
        when(transactionService.saveTransaction(any(Transaction.class), eq(1L)))
                .thenReturn(Optional.of(newTransaction));

        // Execute the method to test
        ResponseEntity<Transaction> response = transactionController.createTransaction(1L, newTransaction);

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(newTransaction, response.getBody());
    }

    @Test
    public void testCreateTransaction_CardNotFound() {
        // Prepare a new transaction
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(new BigDecimal("75.00"));
        newTransaction.setDescription("New Purchase");
        newTransaction.setType("CHARGE");
        newTransaction.setTransactionDate(new Date());

        // Mock service behavior
        when(transactionService.saveTransaction(any(Transaction.class), eq(999L)))
                .thenReturn(Optional.empty());

        // Execute the method to test
        ResponseEntity<Transaction> response = transactionController.createTransaction(999L, newTransaction);

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testDeleteTransaction_Success() {
        // Mock service behavior
        when(transactionService.deleteTransaction(1L)).thenReturn(true);

        // Execute the method to test
        ResponseEntity<Void> response = transactionController.deleteTransaction(1L);

        // Verify the result
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void testDeleteTransaction_NotFound() {
        // Mock service behavior
        when(transactionService.deleteTransaction(999L)).thenReturn(false);

        // Execute the method to test
        ResponseEntity<Void> response = transactionController.deleteTransaction(999L);

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }
}