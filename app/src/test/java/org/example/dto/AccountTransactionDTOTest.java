package org.example.dto;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Unit tests for the AccountTransactionDTO class
 */
public class AccountTransactionDTOTest {
    
    private AccountTransactionDTO dto;
    private Long testId;
    private Date testDate;
    private BigDecimal testAmount;
    private String testDescription;
    private String testType;
    
    @Before
    public void setUp() {
        testId = 1L;
        testDate = new Date();
        testAmount = new BigDecimal("150.25");
        testDescription = "Test Transaction";
        testType = "CREDIT";
        
        // Create a DTO instance with default constructor
        dto = new AccountTransactionDTO();
    }
    
    @Test
    public void testDefaultConstructor() {
        // Verify that a DTO is created properly with the default constructor
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getDate());
        assertNull(dto.getAmount());
        assertNull(dto.getDescription());
        assertNull(dto.getType());
    }
    
    @Test
    public void testParameterizedConstructor() {
        // Create a DTO using the parameterized constructor
        AccountTransactionDTO paramDto = new AccountTransactionDTO(
            testId, testDate, testAmount, testDescription, testType);
        
        // Verify all values are set correctly
        assertEquals(testId, paramDto.getId());
        assertEquals(testDate, paramDto.getDate());
        assertEquals(testAmount, paramDto.getAmount());
        assertEquals(testDescription, paramDto.getDescription());
        assertEquals(testType, paramDto.getType());
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
    public void testDateGetterSetter() {
        dto.setDate(testDate);
        assertEquals(testDate, dto.getDate());
        
        // Test with another value
        Date newDate = new Date(testDate.getTime() + 86400000); // Add a day
        dto.setDate(newDate);
        assertEquals(newDate, dto.getDate());
    }
    
    @Test
    public void testAmountGetterSetter() {
        dto.setAmount(testAmount);
        assertEquals(testAmount, dto.getAmount());
        
        // Test with another value
        BigDecimal newAmount = new BigDecimal("200.50");
        dto.setAmount(newAmount);
        assertEquals(newAmount, dto.getAmount());
    }
    
    @Test
    public void testDescriptionGetterSetter() {
        dto.setDescription(testDescription);
        assertEquals(testDescription, dto.getDescription());
        
        // Test with another value
        String newDescription = "New Test Description";
        dto.setDescription(newDescription);
        assertEquals(newDescription, dto.getDescription());
    }
    
    @Test
    public void testTypeGetterSetter() {
        dto.setType(testType);
        assertEquals(testType, dto.getType());
        
        // Test with another value
        String newType = "CHARGE";
        dto.setType(newType);
        assertEquals(newType, dto.getType());
    }
}
