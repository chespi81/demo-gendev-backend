package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a bank account.
 */
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String accountType; // Current/Demand

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private LocalDateTime creationDate;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTransaction> transactions = new ArrayList<>();

    // Default constructor
    public Account() {
    }

    // Constructor with parameters
    public Account(String accountNumber, String accountType, BigDecimal balance, String ownerId, LocalDateTime creationDate) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.ownerId = ownerId;
        this.creationDate = creationDate;
    }

    // Getters and setters

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    
    public List<AccountTransaction> getTransactions() {
        return transactions;
    }
    
    public void setTransactions(List<AccountTransaction> transactions) {
        this.transactions = transactions;
    }
    
    public void addTransaction(AccountTransaction transaction) {
        transactions.add(transaction);
        transaction.setAccount(this);
    }
    
    public void removeTransaction(AccountTransaction transaction) {
        transactions.remove(transaction);
        transaction.setAccount(null);
    }
}