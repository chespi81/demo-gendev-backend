package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * All endpoints require authentication.
 */
@Tag(name = "Account", description = "Account management API")
@SecurityRequirement(name = "bearer-key")
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
    @Operation(summary = "Get accounts by owner ID", description = "Retrieves all accounts belonging to the specified owner")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Accounts found", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = Account.class)))),
        @ApiResponse(responseCode = "404", description = "No accounts found for owner", content = @Content)
    })
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Account>> getAccountsByOwnerId(
            @Parameter(description = "ID of the owner") @PathVariable String ownerId) {
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
    @Operation(summary = "Get account transactions", description = "Retrieves all transactions for a specific account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transactions found", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountTransactionDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No transactions found for the account", content = @Content)
    })
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<AccountTransactionDTO>> getTransactionsByAccountId(
            @Parameter(description = "ID of the account") @PathVariable Long accountId) {
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
