package org.example.controller;

import org.example.dto.CreditCardDTO;
import org.example.model.CreditCard;
import org.example.model.Transaction;
import org.example.service.CreditCardService;
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
public class CreditCardControllerTest {

    @Mock
    private CreditCardService creditCardService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private CreditCardController creditCardController;

    private CreditCardDTO creditCardDTO1;
    private CreditCardDTO creditCardDTO2;
    private List<CreditCardDTO> creditCardDTOList;
    private Transaction transaction1;
    private Transaction transaction2;
    private List<Transaction> transactionList;
    private SimpleDateFormat dateFormat;

    @Before
    public void setup() {
        // Setup date formatter for consistent date handling
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Setup test credit card DTOs
        creditCardDTO1 = new CreditCardDTO();
        creditCardDTO1.setId(1L);
        creditCardDTO1.setOwnerId("user123");
        creditCardDTO1.setLastFourDigits("3456");
        creditCardDTO1.setCardName("John Doe");
        creditCardDTO1.setValidUntil("12/25");
        creditCardDTO1.setCardType("VISA");
        creditCardDTO1.setStatus(true);
        creditCardDTO1.setLimit(new BigDecimal("5000.00"));
        creditCardDTO1.setBalance(new BigDecimal("1500.00"));

        creditCardDTO2 = new CreditCardDTO();
        creditCardDTO2.setId(2L);
        creditCardDTO2.setOwnerId("user123");
        creditCardDTO2.setLastFourDigits("7654");
        creditCardDTO2.setCardName("John Doe");
        creditCardDTO2.setValidUntil("05/26");
        creditCardDTO2.setCardType("MASTERCARD");
        creditCardDTO2.setStatus(true);
        creditCardDTO2.setLimit(new BigDecimal("10000.00"));
        creditCardDTO2.setBalance(new BigDecimal("3000.00"));

        creditCardDTOList = new ArrayList<>();
        creditCardDTOList.add(creditCardDTO1);
        creditCardDTOList.add(creditCardDTO2);

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
    }

    @Test
    public void testGetCardsByOwnerId_Success() {
        // Mock service behavior
        when(creditCardService.getCardDTOsByOwnerId("user123")).thenReturn(creditCardDTOList);

        // Execute the method to test
        ResponseEntity<List<CreditCardDTO>> response = creditCardController.getCardsByOwnerId("user123");

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(creditCardDTO1.getId(), response.getBody().get(0).getId());
        assertEquals(creditCardDTO2.getId(), response.getBody().get(1).getId());
    }

    @Test
    public void testGetCardsByOwnerId_NotFound() {
        // Mock service behavior
        when(creditCardService.getCardDTOsByOwnerId("nonexistent")).thenReturn(new ArrayList<>());

        // Execute the method to test
        ResponseEntity<List<CreditCardDTO>> response = creditCardController.getCardsByOwnerId("nonexistent");

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testGetCardDTOById_Success() {
        // Mock service behavior
        when(creditCardService.getCardDTOById(1L)).thenReturn(Optional.of(creditCardDTO1));

        // Execute the method to test
        ResponseEntity<CreditCardDTO> response = creditCardController.getCardDTOById(1L);

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(creditCardDTO1.getId(), response.getBody().getId());
        assertEquals(creditCardDTO1.getLastFourDigits(), response.getBody().getLastFourDigits());
    }

    @Test
    public void testGetCardDTOById_NotFound() {
        // Mock service behavior
        when(creditCardService.getCardDTOById(999L)).thenReturn(Optional.empty());

        // Execute the method to test
        ResponseEntity<CreditCardDTO> response = creditCardController.getCardDTOById(999L);

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testGetTransactionsByCardId_Success() {
        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardId(1L)).thenReturn(transactionList);

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = creditCardController.getTransactionsByCardId(1L);

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(transaction1.getId(), response.getBody().get(0).getId());
        assertEquals(transaction2.getId(), response.getBody().get(1).getId());
    }

    @Test
    public void testGetTransactionsByCardId_NotFound() {
        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardId(999L)).thenReturn(new ArrayList<>());

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = creditCardController.getTransactionsByCardId(999L);

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testGetTransactionsByCardIdAndDateRange_Success() throws Exception {
        // Setup dates
        Date startDate = dateFormat.parse("2023-01-01");
        Date endDate = dateFormat.parse("2023-01-31");

        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardIdAndDateRange(1L, startDate, endDate))
                .thenReturn(transactionList);

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = creditCardController.getTransactionsByCardIdAndDateRange(
                1L, startDate, endDate);

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetTransactionsByCardIdAndDateRange_NotFound() throws Exception {
        // Setup dates
        Date startDate = dateFormat.parse("2022-01-01");
        Date endDate = dateFormat.parse("2022-01-31");

        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardIdAndDateRange(1L, startDate, endDate))
                .thenReturn(new ArrayList<>());

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = creditCardController.getTransactionsByCardIdAndDateRange(
                1L, startDate, endDate);

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testGetTransactionsByCardIdAndType_Success() {
        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardIdAndType(1L, "CHARGE"))
                .thenReturn(transactionList);

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = creditCardController.getTransactionsByCardIdAndType(
                1L, "CHARGE");

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("CHARGE", response.getBody().get(0).getType());
        assertEquals("CHARGE", response.getBody().get(1).getType());
    }

    @Test
    public void testGetTransactionsByCardIdAndType_NotFound() {
        // Mock service behavior
        when(transactionService.getTransactionsByCreditCardIdAndType(1L, "CREDIT"))
                .thenReturn(new ArrayList<>());

        // Execute the method to test
        ResponseEntity<List<Transaction>> response = creditCardController.getTransactionsByCardIdAndType(
                1L, "CREDIT");

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testAddTransaction_Success() {
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
        ResponseEntity<Transaction> response = creditCardController.addTransaction(1L, newTransaction);

        // Verify the result
        assertEquals(200, response.getStatusCode().value());
        assertEquals(newTransaction, response.getBody());
    }

    @Test
    public void testAddTransaction_CardNotFound() {
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
        ResponseEntity<Transaction> response = creditCardController.addTransaction(999L, newTransaction);

        // Verify the result
        assertEquals(404, response.getStatusCode().value());
    }
}