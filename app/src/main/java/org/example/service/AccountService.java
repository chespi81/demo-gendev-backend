package org.example.service;

import org.example.model.Account;
import org.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Retrieve all accounts for a specific owner ID.
     *
     * @param ownerId the ID number of the account owner
     * @return list of accounts owned by the specified ID
     */
    public List<Account> getAccountsByOwnerId(String ownerId) {
        return accountRepository.findByOwnerId(ownerId);
    }

    /**
     * Get a specific account by its ID.
     *
     * @param id the account ID
     * @return the account if found, or empty optional otherwise
     */
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    /**
     * Save a new account.
     *
     * @param account the account to save
     * @return the saved account
     */
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Update an existing account.
     *
     * @param id the ID of the account to update
     * @param updatedAccount the updated account details
     * @return the updated account if found, or empty optional otherwise
     */
    public Optional<Account> updateAccount(Long id, Account updatedAccount) {
        if (!accountRepository.existsById(id)) {
            return Optional.empty();
        }
        updatedAccount.setId(id);
        return Optional.of(accountRepository.save(updatedAccount));
    }

    /**
     * Delete an account by its ID.
     *
     * @param id the ID of the account to delete
     * @return true if deleted, false if the account was not found
     */
    public boolean deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            return false;
        }
        accountRepository.deleteById(id);
        return true;
    }
}
