package org.example.service;

import org.example.dto.AccountTransactionDTO;
import org.example.model.Account;
import org.example.model.AccountTransaction;
import org.example.repository.AccountRepository;
import org.example.repository.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for account transaction operations.
 */
@Service
public class AccountTransactionService extends BaseTransactionService<AccountTransaction, Account, AccountTransactionRepository> {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountTransactionService(AccountTransactionRepository accountTransactionRepository, AccountRepository accountRepository) {
        super(accountTransactionRepository);
        this.accountRepository = accountRepository;
    }

    /**
     * Convert AccountTransaction entity to AccountTransactionDTO.
     *
     * @param transaction the transaction entity
     * @return transaction DTO
     */
    private AccountTransactionDTO convertToDTO(AccountTransaction transaction) {
        return new AccountTransactionDTO(
            transaction.getId(),
            transaction.getTransactionDate(),
            transaction.getAmount(),
            transaction.getDescription(),
            transaction.getType()
        );
    }

    /**
     * Get transactions for a specific account.
     *
     * @param accountId the account ID
     * @return list of transactions for the specified account
     */
    @Override
    public List<AccountTransaction> getTransactionsByEntityId(Long accountId) {
        return repository.findByAccountId(accountId);
    }

    /**
     * Get the last N transactions for a specific account.
     *
     * @param accountId the account ID
     * @param count the number of transactions to retrieve
     * @return list of the last N transactions for the specified account
     */
    public List<AccountTransaction> getLastTransactionsByAccountId(Long accountId, int count) {
        return repository.findByAccountId(
            accountId, 
            PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "transactionDate"))
        );
    }

    /**
     * Get transactions for a specific account within a date range.
     *
     * @param accountId the account ID
     * @param startDate start date for the date range
     * @param endDate end date for the date range
     * @return list of transactions for the specified account within the date range
     */
    @Override
    public List<AccountTransaction> getTransactionsByEntityIdAndDateRange(Long accountId, Date startDate, Date endDate) {
        return repository.findByAccountIdAndTransactionDateBetween(accountId, startDate, endDate);
    }

    /**
     * Get transactions for a specific account and type.
     *
     * @param accountId the account ID
     * @param type the transaction type (e.g., CHARGE or CREDIT)
     * @return list of transactions for the specified account and type
     */
    @Override
    public List<AccountTransaction> getTransactionsByEntityIdAndType(Long accountId, String type) {
        return repository.findByAccountIdAndType(accountId, type);
    }

    /**
     * For compatibility with existing API
     */
    public List<AccountTransaction> getTransactionsByAccountId(Long accountId) {
        return getTransactionsByEntityId(accountId);
    }

    public List<AccountTransaction> getTransactionsByAccountIdAndDateRange(Long accountId, Date startDate, Date endDate) {
        return getTransactionsByEntityIdAndDateRange(accountId, startDate, endDate);
    }

    public List<AccountTransaction> getTransactionsByAccountIdAndType(Long accountId, String type) {
        return getTransactionsByEntityIdAndType(accountId, type);
    }

    /**
     * Save a new transaction.
     *
     * @param transaction the transaction to save
     * @param accountId the ID of the account for this transaction
     * @return optional containing the saved transaction if successful
     */
    @Override
    public Optional<AccountTransaction> saveTransaction(AccountTransaction transaction, Long accountId) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.addTransaction(transaction);
            accountRepository.save(account);
            return Optional.of(transaction);
        }
        
        return Optional.empty();
    }

    /**
     * Delete a transaction by its ID.
     * Delegating to parent method.
     *
     * @param id the ID of the transaction to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteTransaction(Long id) {
        return delete(id);
    }

    /**
     * Get transactions for a specific account as DTOs.
     *
     * @param accountId the account ID
     * @return list of transaction DTOs for the specified account
     */
    public List<AccountTransactionDTO> getTransactionDTOsByAccountId(Long accountId) {
        return repository.findByAccountId(accountId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get the last N transactions for a specific account as DTOs.
     *
     * @param accountId the account ID
     * @param count the number of transactions to retrieve
     * @return list of the last N transaction DTOs for the specified account
     */
    public List<AccountTransactionDTO> getLastTransactionDTOsByAccountId(Long accountId, int count) {
        return repository.findByAccountId(
                accountId,
                PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "transactionDate"))
            )
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get transactions for a specific account within a date range as DTOs.
     *
     * @param accountId the account ID
     * @param startDate start date for the date range
     * @param endDate end date for the date range
     * @return list of transaction DTOs for the specified account within the date range
     */
    public List<AccountTransactionDTO> getTransactionDTOsByAccountIdAndDateRange(Long accountId, Date startDate, Date endDate) {
        return repository.findByAccountIdAndTransactionDateBetween(accountId, startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get transactions for a specific account and type as DTOs.
     *
     * @param accountId the account ID
     * @param type the transaction type (e.g., CHARGE or CREDIT)
     * @return list of transaction DTOs for the specified account and type
     */
    public List<AccountTransactionDTO> getTransactionDTOsByAccountIdAndType(Long accountId, String type) {
        return repository.findByAccountIdAndType(accountId, type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
