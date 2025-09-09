package org.example.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Data Transfer Object for AccountTransaction entity.
 * Used to expose account transaction data to clients.
 */
public class AccountTransactionDTO {
    private Long id;
    private Date date;
    private BigDecimal amount;
    private String description;
    private String type; // CHARGE or CREDIT
    
    // Default constructor
    public AccountTransactionDTO() {
    }
    
    // Constructor with parameters
    public AccountTransactionDTO(Long id, Date date, BigDecimal amount, String description, String type) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.type = type;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
