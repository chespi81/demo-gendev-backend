package org.example.service;

import org.example.model.BaseEntity;
import org.example.model.BaseTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Base service for transaction operations
 * 
 * @param <T> Transaction type that extends BaseTransaction
 * @param <E> Entity type that has the transactions
 * @param <R> Repository type for the transaction
 */
public abstract class BaseTransactionService<T extends BaseTransaction, E extends BaseEntity, R extends JpaRepository<T, Long>> 
        extends GenericServiceImpl<T, Long, R> {
    
    public BaseTransactionService(R repository) {
        super(repository);
    }
    
    /**
     * Get transactions by entity ID.
     * 
     * @param entityId the ID of the entity
     * @return list of transactions for the entity
     */
    public abstract List<T> getTransactionsByEntityId(Long entityId);
    
    /**
     * Get transactions by entity ID and date range.
     * 
     * @param entityId the ID of the entity
     * @param startDate the start date
     * @param endDate the end date
     * @return list of transactions for the entity within the date range
     */
    public abstract List<T> getTransactionsByEntityIdAndDateRange(Long entityId, Date startDate, Date endDate);
    
    /**
     * Get transactions by entity ID and type.
     * 
     * @param entityId the ID of the entity
     * @param type the transaction type
     * @return list of transactions for the entity of the specified type
     */
    public abstract List<T> getTransactionsByEntityIdAndType(Long entityId, String type);
    
    /**
     * Save a transaction for an entity.
     * 
     * @param transaction the transaction to save
     * @param entityId the ID of the entity
     * @return the saved transaction
     */
    public abstract Optional<T> saveTransaction(T transaction, Long entityId);
}
