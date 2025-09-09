package org.example.repository;

import org.example.model.AccountTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for AccountTransaction entities.
 */
@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
    
    /**
     * Find transactions by account ID.
     *
     * @param accountId the account ID
     * @return list of transactions for the specified account
     */
    List<AccountTransaction> findByAccountId(Long accountId);
    
    /**
     * Find transactions by account ID with pagination.
     *
     * @param accountId the account ID
     * @param pageable pagination information
     * @return list of transactions for the specified account with pagination
     */
    List<AccountTransaction> findByAccountId(Long accountId, Pageable pageable);
    
    /**
     * Find transactions by account ID and a date range.
     *
     * @param accountId the account ID
     * @param startDate start date for the date range
     * @param endDate end date for the date range
     * @return list of transactions for the specified account within the date range
     */
    List<AccountTransaction> findByAccountIdAndTransactionDateBetween(Long accountId, Date startDate, Date endDate);
    
    /**
     * Find transactions by account ID and type.
     *
     * @param accountId the account ID
     * @param type the transaction type (e.g., CHARGE or CREDIT)
     * @return list of transactions for the specified account and type
     */
    List<AccountTransaction> findByAccountIdAndType(Long accountId, String type);
}
