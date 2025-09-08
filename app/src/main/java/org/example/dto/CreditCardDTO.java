package org.example.dto;

import org.example.model.CreditCard;

import java.math.BigDecimal;

/**
 * Data Transfer Object for CreditCard without transactions.
 */
public class CreditCardDTO {
    private Long id;
    private String lastFourDigits;
    private String cardName;
    private String validUntil;
    private String cardType;
    private boolean status;
    private BigDecimal limit;
    private BigDecimal balance;
    private String ownerId;

    // Default constructor
    public CreditCardDTO() {
    }

    // Constructor from CreditCard entity
    public CreditCardDTO(CreditCard creditCard) {
        this.id = creditCard.getId();
        this.lastFourDigits = creditCard.getLastFourDigits();
        this.cardName = creditCard.getCardName();
        this.validUntil = creditCard.getValidUntil();
        this.cardType = creditCard.getCardType();
        this.status = creditCard.isStatus();
        this.limit = creditCard.getLimit();
        this.balance = creditCard.getBalance();
        this.ownerId = creditCard.getOwnerId();
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
