package org.example.repository;

import org.example.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Find all transactions for a specific credit card.
     * 
     * @param creditCardId the ID of the credit card
     * @return list of transactions for the specified credit card
     */
    List<Transaction> findByCreditCardId(Long creditCardId);
    
    /**
     * Find all transactions for a specific credit card within a date range.
     * 
     * @param creditCardId the ID of the credit card
     * @param startDate the start date for filtering transactions
     * @param endDate the end date for filtering transactions
     * @return list of transactions within the specified date range for the given credit card
     */
    List<Transaction> findByCreditCardIdAndTransactionDateBetween(Long creditCardId, Date startDate, Date endDate);
    
    /**
     * Find all transactions for a specific credit card by type (CHARGE/CREDIT).
     * 
     * @param creditCardId the ID of the credit card
     * @param type the type of transaction (CHARGE or CREDIT)
     * @return list of transactions of the specified type for the given credit card
     */
    List<Transaction> findByCreditCardIdAndType(Long creditCardId, String type);
}
