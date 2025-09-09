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
public class TransactionService extends BaseTransactionService<Transaction, CreditCard, TransactionRepository> {

    private final CreditCardRepository creditCardRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CreditCardRepository creditCardRepository) {
        super(transactionRepository);
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
    @Override
    public List<Transaction> getTransactionsByEntityId(Long creditCardId) {
        return repository.findByCreditCardId(creditCardId);
    }

    /**
     * Retrieve all transactions for a specific credit card within a date range.
     *
     * @param creditCardId the ID of the credit card
     * @param startDate the start date for filtering transactions
     * @param endDate the end date for filtering transactions
     * @return list of transactions within the specified date range for the given credit card
     */
    @Override
    public List<Transaction> getTransactionsByEntityIdAndDateRange(Long creditCardId, Date startDate, Date endDate) {
        return repository.findByCreditCardIdAndTransactionDateBetween(creditCardId, startDate, endDate);
    }

    /**
     * Retrieve all transactions for a specific credit card by type (CHARGE/CREDIT).
     *
     * @param creditCardId the ID of the credit card
     * @param type the type of transaction (CHARGE or CREDIT)
     * @return list of transactions of the specified type for the given credit card
     */
    @Override
    public List<Transaction> getTransactionsByEntityIdAndType(Long creditCardId, String type) {
        return repository.findByCreditCardIdAndType(creditCardId, type);
    }

    /**
     * For compatibility with existing API
     */
    public List<Transaction> getTransactionsByCreditCardId(Long creditCardId) {
        return getTransactionsByEntityId(creditCardId);
    }

    public List<Transaction> getTransactionsByCreditCardIdAndDateRange(Long creditCardId, Date startDate, Date endDate) {
        return getTransactionsByEntityIdAndDateRange(creditCardId, startDate, endDate);
    }

    public List<Transaction> getTransactionsByCreditCardIdAndType(Long creditCardId, String type) {
        return getTransactionsByEntityIdAndType(creditCardId, type);
    }
    
    /**
     * Get a specific transaction by its ID.
     * Delegating to parent method for backward compatibility.
     *
     * @param id the transaction ID
     * @return the transaction if found, or empty optional otherwise
     */
    public Optional<Transaction> getTransactionById(Long id) {
        return getById(id);
    }
    
    /**
     * Delete a transaction by its ID.
     * Delegating to parent method for backward compatibility.
     *
     * @param id the ID of the transaction to delete
     * @return true if deleted, false if the transaction was not found
     */
    public boolean deleteTransaction(Long id) {
        return delete(id);
    }

    /**
     * Save a new transaction.
     *
     * @param transaction the transaction to save
     * @param creditCardId the ID of the associated credit card
     * @return the saved transaction if the credit card exists, or empty optional otherwise
     */
    @Override
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
     * Retrieve all transactions for a specific client account as DTOs.
     *
     * @param clientId the ID of the client account
     * @return list of transaction DTOs for the specified client account
     */
    public List<TransactionDTO> getTransactionsByClientId(String clientId) {
        List<CreditCard> clientCards = creditCardRepository.findByOwnerId(clientId);
        
        return clientCards.stream()
                .flatMap(card -> repository.findByCreditCardId(card.getId()).stream())
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
        return repository.findByCreditCardId(creditCardId).stream()
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
        return repository.findByCreditCardIdAndTransactionDateBetween(creditCardId, startDate, endDate).stream()
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
        return repository.findByCreditCardIdAndType(creditCardId, type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
