package org.example.dto;

import org.example.model.CreditCard;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;

/**
 * Unit tests for the CreditCardDTO class
 */
public class CreditCardDTOTest {
    
    private CreditCardDTO dto;
    private Long testId;
    private String testLastFourDigits;
    private String testCardName;
    private String testValidUntil;
    private String testCardType;
    private boolean testStatus;
    private BigDecimal testLimit;
    private BigDecimal testBalance;
    private String testOwnerId;
    private CreditCard testCreditCard;
    
    @Before
    public void setUp() {
        testId = 1L;
        testLastFourDigits = "1234";
        testCardName = "Test Card";
        testValidUntil = "12/25";
        testCardType = "VISA";
        testStatus = true;
        testLimit = new BigDecimal("5000.00");
        testBalance = new BigDecimal("1500.50");
        testOwnerId = "user123";
        
        // Create a DTO instance with default constructor
        dto = new CreditCardDTO();
        
        // Create a credit card entity for testing the constructor
        testCreditCard = new CreditCard();
        testCreditCard.setId(testId);
        testCreditCard.setLastFourDigits(testLastFourDigits);
        testCreditCard.setCardName(testCardName);
        testCreditCard.setValidUntil(testValidUntil);
        testCreditCard.setCardType(testCardType);
        testCreditCard.setStatus(testStatus);
        testCreditCard.setLimit(testLimit);
        testCreditCard.setBalance(testBalance);
        testCreditCard.setOwnerId(testOwnerId);
    }
    
    @Test
    public void testDefaultConstructor() {
        // Verify that a DTO is created properly with the default constructor
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getLastFourDigits());
        assertNull(dto.getCardName());
        assertNull(dto.getValidUntil());
        assertNull(dto.getCardType());
        assertFalse(dto.isStatus());
        assertNull(dto.getLimit());
        assertNull(dto.getBalance());
        assertNull(dto.getOwnerId());
    }
    
    @Test
    public void testEntityConstructor() {
        // Create a DTO from the credit card entity
        CreditCardDTO entityDto = new CreditCardDTO(testCreditCard);
        
        // Verify all values are mapped correctly from the entity
        assertEquals(testId, entityDto.getId());
        assertEquals(testLastFourDigits, entityDto.getLastFourDigits());
        assertEquals(testCardName, entityDto.getCardName());
        assertEquals(testValidUntil, entityDto.getValidUntil());
        assertEquals(testCardType, entityDto.getCardType());
        assertEquals(testStatus, entityDto.isStatus());
        assertEquals(testLimit, entityDto.getLimit());
        assertEquals(testBalance, entityDto.getBalance());
        assertEquals(testOwnerId, entityDto.getOwnerId());
    }
    
    @Test
    public void testIdGetterSetter() {
        dto.setId(testId);
        assertEquals(testId, dto.getId());
        
        // Test with another value
        Long newId = 2L;
        dto.setId(newId);
        assertEquals(newId, dto.getId());
    }
    
    @Test
    public void testLastFourDigitsGetterSetter() {
        dto.setLastFourDigits(testLastFourDigits);
        assertEquals(testLastFourDigits, dto.getLastFourDigits());
        
        // Test with another value
        String newLastFourDigits = "5678";
        dto.setLastFourDigits(newLastFourDigits);
        assertEquals(newLastFourDigits, dto.getLastFourDigits());
    }
    
    @Test
    public void testCardNameGetterSetter() {
        dto.setCardName(testCardName);
        assertEquals(testCardName, dto.getCardName());
        
        // Test with another value
        String newCardName = "New Test Card";
        dto.setCardName(newCardName);
        assertEquals(newCardName, dto.getCardName());
    }
    
    @Test
    public void testValidUntilGetterSetter() {
        dto.setValidUntil(testValidUntil);
        assertEquals(testValidUntil, dto.getValidUntil());
        
        // Test with another value
        String newValidUntil = "01/27";
        dto.setValidUntil(newValidUntil);
        assertEquals(newValidUntil, dto.getValidUntil());
    }
    
    @Test
    public void testCardTypeGetterSetter() {
        dto.setCardType(testCardType);
        assertEquals(testCardType, dto.getCardType());
        
        // Test with another value
        String newCardType = "MASTERCARD";
        dto.setCardType(newCardType);
        assertEquals(newCardType, dto.getCardType());
    }
    
    @Test
    public void testStatusGetterSetter() {
        dto.setStatus(testStatus);
        assertEquals(testStatus, dto.isStatus());
        
        // Test with opposite value
        boolean newStatus = false;
        dto.setStatus(newStatus);
        assertEquals(newStatus, dto.isStatus());
    }
    
    @Test
    public void testLimitGetterSetter() {
        dto.setLimit(testLimit);
        assertEquals(testLimit, dto.getLimit());
        
        // Test with another value
        BigDecimal newLimit = new BigDecimal("10000.00");
        dto.setLimit(newLimit);
        assertEquals(newLimit, dto.getLimit());
    }
    
    @Test
    public void testBalanceGetterSetter() {
        dto.setBalance(testBalance);
        assertEquals(testBalance, dto.getBalance());
        
        // Test with another value
        BigDecimal newBalance = new BigDecimal("2500.75");
        dto.setBalance(newBalance);
        assertEquals(newBalance, dto.getBalance());
    }
    
    @Test
    public void testOwnerIdGetterSetter() {
        dto.setOwnerId(testOwnerId);
        assertEquals(testOwnerId, dto.getOwnerId());
        
        // Test with another value
        String newOwnerId = "user456";
        dto.setOwnerId(newOwnerId);
        assertEquals(newOwnerId, dto.getOwnerId());
    }
}
