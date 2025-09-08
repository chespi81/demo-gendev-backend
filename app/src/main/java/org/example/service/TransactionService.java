package org.example.service;

import org.example.model.CreditCard;
import org.example.model.Transaction;
import org.example.repository.CreditCardRepository;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            return Optional.of(transactionRepository.save(transaction));
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
}
