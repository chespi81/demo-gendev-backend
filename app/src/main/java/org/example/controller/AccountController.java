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
public class AccountController extends BaseController<Account, Long, AccountService> {

    private final AccountTransactionService accountTransactionService;

    @Autowired
    public AccountController(AccountService accountService, AccountTransactionService accountTransactionService) {
        super(accountService);
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
        List<Account> accounts = service.getAccountsByOwnerId(ownerId);
        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accounts);
    }

    @Override
    public ResponseEntity<List<Account>> getByOwnerId(String ownerId) {
        return getAccountsByOwnerId(ownerId);
    }

    // The following methods are inherited from BaseController:
    // getById, create, update, delete
    
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
