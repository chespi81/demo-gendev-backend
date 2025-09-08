package org.example.controller;

import org.example.model.Transaction;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * REST controller that provides endpoints for credit card transaction operations.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Get all transactions for a specific credit card.
     * 
     * @param creditCardId the ID of the credit card
     * @return list of transactions for the specified credit card
     */
    @GetMapping("/card/{creditCardId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCreditCardId(@PathVariable Long creditCardId) {
        List<Transaction> transactions = transactionService.getTransactionsByCreditCardId(creditCardId);
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get all transactions for a specific credit card within a date range.
     * 
     * @param creditCardId the ID of the credit card
     * @param startDate the start date for filtering transactions
     * @param endDate the end date for filtering transactions
     * @return list of transactions within the specified date range for the given credit card
     */
    @GetMapping("/card/{creditCardId}/daterange")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @PathVariable Long creditCardId,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        
        List<Transaction> transactions = transactionService.getTransactionsByCreditCardIdAndDateRange(
                creditCardId, startDate, endDate);
        
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get all transactions for a specific credit card by type (CHARGE/CREDIT).
     * 
     * @param creditCardId the ID of the credit card
     * @param type the type of transaction (CHARGE or CREDIT)
     * @return list of transactions of the specified type for the given credit card
     */
    @GetMapping("/card/{creditCardId}/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByType(
            @PathVariable Long creditCardId, 
            @PathVariable String type) {
        
        List<Transaction> transactions = transactionService.getTransactionsByCreditCardIdAndType(creditCardId, type);
        
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get a specific transaction by its ID.
     * 
     * @param id the transaction ID
     * @return the transaction if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new transaction for a credit card.
     * 
     * @param creditCardId the ID of the credit card
     * @param transaction the transaction to create
     * @return the created transaction
     */
    @PostMapping("/card/{creditCardId}")
    public ResponseEntity<Transaction> createTransaction(
            @PathVariable Long creditCardId, 
            @RequestBody Transaction transaction) {
        
        return transactionService.saveTransaction(transaction, creditCardId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete a transaction by its ID.
     * 
     * @param id the ID of the transaction to delete
     * @return no content if deleted, not found if the transaction was not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        boolean deleted = transactionService.deleteTransaction(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
