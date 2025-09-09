package org.example.controller;

import org.example.dto.AccountTransactionDTO;
import org.example.model.Account;
import org.example.model.AccountTransaction;
import org.example.service.AccountService;
import org.example.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that provides endpoints for account operations.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountTransactionService accountTransactionService;

    @Autowired
    public AccountController(AccountService accountService, AccountTransactionService accountTransactionService) {
        this.accountService = accountService;
        this.accountTransactionService = accountTransactionService;
    }

    /**
     * Get all accounts for a specific owner ID.
     * 
     * @param ownerId the ID number of the account owner
     * @return list of accounts owned by the specified ID
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Account>> getAccountsByOwnerId(@PathVariable String ownerId) {
        List<Account> accounts = accountService.getAccountsByOwnerId(ownerId);
        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accounts);
    }

    /**
     * Get a specific account by its ID.
     * 
     * @param id the account ID
     * @return the account if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new account.
     * 
     * @param account the account to create
     * @return the created account
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account savedAccount = accountService.saveAccount(account);
        return ResponseEntity.ok(savedAccount);
    }

    /**
     * Update an existing account.
     * 
     * @param id the ID of the account to update
     * @param updatedAccount the updated account details
     * @return the updated account if found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
        return accountService.updateAccount(id, updatedAccount)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete an account by its ID.
     * 
     * @param id the ID of the account to delete
     * @return no content if deleted, not found if the account was not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        boolean deleted = accountService.deleteAccount(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    /**
     * Get all transactions for an account.
     * 
     * @param accountId the account ID
     * @return list of transactions for the specified account
     */
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<AccountTransactionDTO>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<AccountTransactionDTO> transactions = accountTransactionService.getTransactionDTOsByAccountId(accountId);
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * Get the last N transactions for an account.
     * 
     * @param accountId the account ID
     * @param count the number of transactions to retrieve (default 20)
     * @return list of the last N transactions for the specified account
     */
    @GetMapping("/{accountId}/transactions/last")
    public ResponseEntity<List<AccountTransactionDTO>> getLastTransactionsByAccountId(
            @PathVariable Long accountId,
            @RequestParam(defaultValue = "20") int count) {
        List<AccountTransactionDTO> transactions = accountTransactionService.getLastTransactionDTOsByAccountId(accountId, count);
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * Get transactions for an account by type (CHARGE or CREDIT).
     * 
     * @param accountId the account ID
     * @param type the transaction type (CHARGE or CREDIT)
     * @return list of transactions for the specified account and type
     */
    @GetMapping("/{accountId}/transactions/type/{type}")
    public ResponseEntity<List<AccountTransactionDTO>> getTransactionsByAccountIdAndType(
            @PathVariable Long accountId, 
            @PathVariable String type) {
        List<AccountTransactionDTO> transactions = accountTransactionService.getTransactionDTOsByAccountIdAndType(accountId, type);
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * Add a new transaction to an account.
     * 
     * @param accountId the account ID
     * @param transaction the transaction to add
     * @return the created transaction
     */
    @PostMapping("/{accountId}/transactions")
    public ResponseEntity<AccountTransaction> addTransaction(
            @PathVariable Long accountId,
            @RequestBody AccountTransaction transaction) {
        
        return accountTransactionService.saveTransaction(transaction, accountId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
