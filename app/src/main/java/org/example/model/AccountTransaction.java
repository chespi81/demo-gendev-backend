package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity class representing an account transaction.
 */
@Entity
@Table(name = "account_transactions")
public class AccountTransaction extends BaseTransaction {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // Default constructor
    public AccountTransaction() {
    }

    // Constructor with parameters
    public AccountTransaction(Date transactionDate, BigDecimal amount, String description, String type, Account account) {
        setTransactionDate(transactionDate);
        setAmount(amount);
        setDescription(description);
        setType(type);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}