package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity class representing a credit card transaction.
 */
@Entity
@Table(name = "transactions")
public class Transaction extends BaseTransaction {

    @ManyToOne
    @JoinColumn(name = "credit_card_id", nullable = false)
    private CreditCard creditCard;

    // Default constructor
    public Transaction() {
    }

    // Constructor with parameters
    public Transaction(Date transactionDate, BigDecimal amount, String description, String type, CreditCard creditCard) {
        setTransactionDate(transactionDate);
        setAmount(amount);
        setDescription(description);
        setType(type);
        this.creditCard = creditCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
