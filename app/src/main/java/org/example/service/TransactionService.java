package org.example.service;

import org.example.dto.TransactionDTO;
import org.example.model.CreditCard;
import org.example.model.Transaction;
import org.example.repository.CreditCardRepository;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CreditCardRepository creditCardRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CreditCardRepository creditCardRepository) {
        this.transactionRepository = transactionRepository;
        this.creditCardRepository = creditCardRepository;
    }
    
    /**
     * Convert Transaction entity to TransactionDTO.
     *
     * @param transaction the transaction entity
     * @return the transaction DTO
     */
    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
            transaction.getId(),
            transaction.getTransactionDate(),
            transaction.getAmount(),
            transaction.getDescription(),
            transaction.getType()
        );
    }

    /**
     * Retrieve all transactions for a specific credit card.
     *
     * @param creditCardId the ID of the credit card
     * @return list of transactions for the specified credit card, or empty list if none found
     */
    public List<Transaction> getTransactionsByCreditCardId(Long creditCardId) {
        return transactionRepository.findByCreditCardId(creditCardId);
    }

    /**
     * Retrieve all transactions for a specific credit card within a date range.
     *
     * @param creditCardId the ID of the credit card
     * @param startDate the start date for filtering transactions
     * @param endDate the end date for filtering transactions
     * @return list of transactions within the specified date range for the given credit card
     */
    public List<Transaction> getTransactionsByCreditCardIdAndDateRange(Long creditCardId, Date startDate, Date endDate) {
        return transactionRepository.findByCreditCardIdAndTransactionDateBetween(creditCardId, startDate, endDate);
    }

    /**
     * Retrieve all transactions for a specific credit card by type (CHARGE/CREDIT).
     *
     * @param creditCardId the ID of the credit card
     * @param type the type of transaction (CHARGE or CREDIT)
     * @return list of transactions of the specified type for the given credit card
     */
    public List<Transaction> getTransactionsByCreditCardIdAndType(Long creditCardId, String type) {
        return transactionRepository.findByCreditCardIdAndType(creditCardId, type);
    }

    /**
     * Get a specific transaction by its ID.
     *
     * @param id the transaction ID
     * @return the transaction if found, or empty optional otherwise
     */
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    /**
     * Save a new transaction.
     *
     * @param transaction the transaction to save
     * @param creditCardId the ID of the associated credit card
     * @return the saved transaction if the credit card exists, or empty optional otherwise
     */
    public Optional<Transaction> saveTransaction(Transaction transaction, Long creditCardId) {
        Optional<CreditCard> creditCardOptional = creditCardRepository.findById(creditCardId);
        
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            transaction.setCreditCard(creditCard);
            creditCard.addTransaction(transaction);
            creditCardRepository.save(creditCard);
            return Optional.of(transaction);
        }
        
        return Optional.empty();
    }

    /**
     * Delete a transaction by its ID.
     *
     * @param id the ID of the transaction to delete
     * @return true if deleted, false if the transaction was not found
     */
    public boolean deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            return false;
        }
        transactionRepository.deleteById(id);
        return true;
    }
    
    /**
     * Retrieve all transactions for a specific client account as DTOs.
     *
     * @param clientId the ID of the client account
     * @return list of transaction DTOs for the specified client account
     */
    public List<TransactionDTO> getTransactionsByClientId(String clientId) {
        List<CreditCard> clientCards = creditCardRepository.findByOwnerId(clientId);
        
        return clientCards.stream()
                .flatMap(card -> transactionRepository.findByCreditCardId(card.getId()).stream())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieve all transactions for a specific credit card as DTOs.
     *
     * @param creditCardId the ID of the credit card
     * @return list of transaction DTOs for the specified credit card
     */
    public List<TransactionDTO> getTransactionDTOsByCreditCardId(Long creditCardId) {
        return transactionRepository.findByCreditCardId(creditCardId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieve all transactions for a specific credit card within a date range as DTOs.
     *
     * @param creditCardId the ID of the credit card
     * @param startDate the start date for filtering transactions
     * @param endDate the end date for filtering transactions
     * @return list of transaction DTOs within the specified date range for the given credit card
     */
    public List<TransactionDTO> getTransactionDTOsByCreditCardIdAndDateRange(Long creditCardId, Date startDate, Date endDate) {
        return transactionRepository.findByCreditCardIdAndTransactionDateBetween(creditCardId, startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieve all transactions for a specific credit card by type (CHARGE/CREDIT) as DTOs.
     *
     * @param creditCardId the ID of the credit card
     * @param type the type of transaction (CHARGE or CREDIT)
     * @return list of transaction DTOs of the specified type for the given credit card
     */
    public List<TransactionDTO> getTransactionDTOsByCreditCardIdAndType(Long creditCardId, String type) {
        return transactionRepository.findByCreditCardIdAndType(creditCardId, type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
