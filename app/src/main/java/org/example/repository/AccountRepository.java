package org.example.repository;

import org.example.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Find all accounts by owner ID.
     * 
     * @param ownerId the ID number of the account owner
     * @return list of accounts owned by the specified ID
     */
    List<Account> findByOwnerId(String ownerId);
}
