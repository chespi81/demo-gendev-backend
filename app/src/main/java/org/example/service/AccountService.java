package org.example.service;

import org.example.model.Account;
import org.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService extends GenericServiceImpl<Account, Long, AccountRepository> {

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        super(accountRepository);
    }

    /**
     * Retrieve all accounts for a specific owner ID.
     *
     * @param ownerId the ID number of the account owner
     * @return list of accounts owned by the specified ID
     */
    public List<Account> getAccountsByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    /**
     * Get a specific account by its ID.
     * Delegating to the parent method for backward compatibility.
     *
     * @param id the account ID
     * @return the account if found, or empty optional otherwise
     */
    public Optional<Account> getAccountById(Long id) {
        return getById(id);
    }

    /**
     * Save a new account.
     * Delegating to the parent method for backward compatibility.
     *
     * @param account the account to save
     * @return the saved account
     */
    public Account saveAccount(Account account) {
        return save(account);
    }

    /**
     * Update an existing account.
     * Delegating to the parent method for backward compatibility.
     *
     * @param id the ID of the account to update
     * @param updatedAccount the updated account details
     * @return the updated account if found, or empty optional otherwise
     */
    public Optional<Account> updateAccount(Long id, Account updatedAccount) {
        return update(id, updatedAccount);
    }

    /**
     * Delete an account by its ID.
     * Delegating to the parent method for backward compatibility.
     *
     * @param id the ID of the account to delete
     * @return true if deleted, false if the account was not found
     */
    public boolean deleteAccount(Long id) {
        return delete(id);
    }
}
