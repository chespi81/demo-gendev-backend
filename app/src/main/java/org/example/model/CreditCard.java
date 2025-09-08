package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.math.BigDecimal;

/**
 * Entity class representing a credit card.
 */
@Entity
@Table(name = "credit_cards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lastFourDigits; // Obfuscated card number, only last 4 digits

    @Column(nullable = false)
    private String cardName; // Card owner name
    
    @Column(nullable = false)
    private String validUntil; // Obfuscated expiration date
    
    @Column(nullable = false)
    private String cardType; // Debit/Credit
    
    @Column(nullable = false)
    private boolean status; // On/Off
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal limit; // Total amount that can be borrowed
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance; // Remaining amount to be borrowed
    
    @Column(nullable = false)
    private String ownerId; // ID number of the card owner

    // Default constructor
    public CreditCard() {
    }

    // Constructor with parameters
    public CreditCard(String lastFourDigits, String cardName, String validUntil, String cardType, 
                    boolean status, BigDecimal limit, BigDecimal balance, String ownerId) {
        this.lastFourDigits = lastFourDigits;
        this.cardName = cardName;
        this.validUntil = validUntil;
        this.cardType = cardType;
        this.status = status;
        this.limit = limit;
        this.balance = balance;
        this.ownerId = ownerId;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
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
}
