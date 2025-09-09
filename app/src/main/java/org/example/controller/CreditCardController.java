package org.example.controller;

import org.example.dto.CreditCardDTO;
import org.example.model.CreditCard;
import org.example.model.Transaction;
import org.example.service.CreditCardService;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * REST controller that provides endpoints for credit card operations.
 */
@RestController
@RequestMapping("/api/cards")
public class CreditCardController extends BaseController<CreditCard, Long, CreditCardService> {

    private final TransactionService transactionService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService, TransactionService transactionService) {
        super(creditCardService);
        this.transactionService = transactionService;
    }

    /**
     * Get all credit cards for a specific owner ID.
     * This endpoint returns cards without their transactions.
     * 
     * @param ownerId the ID number of the card owner
     * @return list of credit cards owned by the specified ID (without transactions)
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<CreditCardDTO>> getCardsByOwnerId(@PathVariable String ownerId) {
        List<CreditCardDTO> cardDTOs = service.getCardDTOsByOwnerId(ownerId);
        if (cardDTOs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cardDTOs);
    }

    @Override
    public ResponseEntity<List<CreditCardDTO>> getByOwnerId(String ownerId) {
        return getCardsByOwnerId(ownerId);
    }

    /**
     * Get a specific credit card by its ID (with DTO transformation).
     * This endpoint returns a card without its transactions.
     * This method overrides the BaseController.getById to return a DTO instead of an entity.
     * 
     * @param id the credit card ID
     * @return the credit card DTO if found (without transactions)
     */
    @GetMapping("/{id}/dto")
    public ResponseEntity<CreditCardDTO> getCardDTOById(@PathVariable Long id) {
        return service.getCardDTOById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // The following methods are inherited from BaseController:
    // getById (returns entity), create, update, delete
    
    /**
     * Get all transactions for a specific credit card.
     *
     * @param cardId the ID of the credit card
     * @return list of transactions for the specified credit card
     */
    @GetMapping("/{cardId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsByCardId(@PathVariable Long cardId) {
        List<Transaction> transactions = transactionService.getTransactionsByCreditCardId(cardId);
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * Get all transactions for a specific credit card within a date range.
     *
     * @param cardId the ID of the credit card
     * @param startDate the start date for filtering transactions
     * @param endDate the end date for filtering transactions
     * @return list of transactions within the specified date range for the given credit card
     */
    @GetMapping("/{cardId}/transactions/daterange")
    public ResponseEntity<List<Transaction>> getTransactionsByCardIdAndDateRange(
            @PathVariable Long cardId,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        
        List<Transaction> transactions = transactionService.getTransactionsByCreditCardIdAndDateRange(
                cardId, startDate, endDate);
        
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * Get all transactions for a specific credit card by type (CHARGE/CREDIT).
     *
     * @param cardId the ID of the credit card
     * @param type the type of transaction (CHARGE or CREDIT)
     * @return list of transactions of the specified type for the given credit card
     */
    @GetMapping("/{cardId}/transactions/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByCardIdAndType(
            @PathVariable Long cardId,
            @PathVariable String type) {
        
        List<Transaction> transactions = transactionService.getTransactionsByCreditCardIdAndType(cardId, type);
        
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * Add a new transaction to a credit card.
     *
     * @param cardId the ID of the credit card
     * @param transaction the transaction to add
     * @return the added transaction if the card exists
     */
    @PostMapping("/{cardId}/transactions")
    public ResponseEntity<Transaction> addTransaction(
            @PathVariable Long cardId,
            @RequestBody Transaction transaction) {
        
        return transactionService.saveTransaction(transaction, cardId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
