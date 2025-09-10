package org.example.service;

import org.example.dto.CreditCardDTO;
import org.example.model.CreditCard;
import org.example.repository.CreditCardRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardService creditCardService;

    private CreditCard testCard;
    private final Long testId = 1L;
    private final String testOwnerId = "TEST123";
    private final String testLastFourDigits = "1234";
    private final String testCardName = "Test User";
    private final String testValidUntil = "12/25";
    private final String testCardType = "VISA";
    private final boolean testStatus = true;
    private final BigDecimal testLimit = new BigDecimal("5000.00");
    private final BigDecimal testBalance = new BigDecimal("1500.75");

    @Before
    public void setUp() {
        testCard = new CreditCard();
        testCard.setId(testId);
        testCard.setOwnerId(testOwnerId);
        testCard.setLastFourDigits(testLastFourDigits);
        testCard.setCardName(testCardName);
        testCard.setValidUntil(testValidUntil);
        testCard.setCardType(testCardType);
        testCard.setStatus(testStatus);
        testCard.setLimit(testLimit);
        testCard.setBalance(testBalance);
    }

    @Test
    public void testGetCardsByOwnerId() {
        // Arrange
        List<CreditCard> expectedCards = Arrays.asList(testCard);
        when(creditCardRepository.findByOwnerId(testOwnerId)).thenReturn(expectedCards);

        // Act
        List<CreditCard> resultCards = creditCardService.getCardsByOwnerId(testOwnerId);

        // Assert
        assertEquals(expectedCards, resultCards);
        verify(creditCardRepository).findByOwnerId(testOwnerId);
    }

    @Test
    public void testGetCardById_WhenCardExists() {
        // Arrange
        when(creditCardRepository.findById(testId)).thenReturn(Optional.of(testCard));

        // Act
        Optional<CreditCard> result = creditCardService.getCardById(testId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testCard, result.get());
        verify(creditCardRepository).findById(testId);
    }

    @Test
    public void testGetCardById_WhenCardDoesNotExist() {
        // Arrange
        when(creditCardRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        Optional<CreditCard> result = creditCardService.getCardById(testId);

        // Assert
        assertFalse(result.isPresent());
        verify(creditCardRepository).findById(testId);
    }

    @Test
    public void testSaveCard() {
        // Arrange
        when(creditCardRepository.save(testCard)).thenReturn(testCard);

        // Act
        CreditCard result = creditCardService.saveCard(testCard);

        // Assert
        assertEquals(testCard, result);
        verify(creditCardRepository).save(testCard);
    }

    @Test
    public void testUpdateCard_WhenCardExists() {
        // Arrange
        CreditCard updatedCard = new CreditCard();
        updatedCard.setOwnerId(testOwnerId);
        updatedCard.setLastFourDigits(testLastFourDigits);
        updatedCard.setCardName(testCardName);
        updatedCard.setValidUntil(testValidUntil);
        updatedCard.setCardType("MASTERCARD");
        updatedCard.setStatus(true);
        updatedCard.setLimit(new BigDecimal("10000.00"));
        updatedCard.setBalance(new BigDecimal("2000.00"));

        when(creditCardRepository.existsById(testId)).thenReturn(true);
        when(creditCardRepository.save(any(CreditCard.class))).thenAnswer(invocation -> {
            CreditCard savedCard = invocation.getArgument(0);
            assertEquals(testId, savedCard.getId());
            return savedCard;
        });

        // Act
        Optional<CreditCard> result = creditCardService.updateCard(testId, updatedCard);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testId, result.get().getId());
        assertEquals("MASTERCARD", result.get().getCardType());
        assertEquals(new BigDecimal("10000.00"), result.get().getLimit());
        verify(creditCardRepository).existsById(testId);
        verify(creditCardRepository).save(any(CreditCard.class));
    }

    @Test
    public void testUpdateCard_WhenCardDoesNotExist() {
        // Arrange
        CreditCard updatedCard = new CreditCard();
        when(creditCardRepository.existsById(testId)).thenReturn(false);

        // Act
        Optional<CreditCard> result = creditCardService.updateCard(testId, updatedCard);

        // Assert
        assertFalse(result.isPresent());
        verify(creditCardRepository).existsById(testId);
        verify(creditCardRepository, never()).save(any(CreditCard.class));
    }

    @Test
    public void testDeleteCard_WhenCardExists() {
        // Arrange
        when(creditCardRepository.existsById(testId)).thenReturn(true);
        doNothing().when(creditCardRepository).deleteById(testId);

        // Act
        boolean result = creditCardService.deleteCard(testId);

        // Assert
        assertTrue(result);
        verify(creditCardRepository).existsById(testId);
        verify(creditCardRepository).deleteById(testId);
    }

    @Test
    public void testDeleteCard_WhenCardDoesNotExist() {
        // Arrange
        when(creditCardRepository.existsById(testId)).thenReturn(false);

        // Act
        boolean result = creditCardService.deleteCard(testId);

        // Assert
        assertFalse(result);
        verify(creditCardRepository).existsById(testId);
        verify(creditCardRepository, never()).deleteById(any());
    }

    @Test
    public void testGetCardDTOsByOwnerId() {
        // Arrange
        List<CreditCard> cards = Arrays.asList(testCard);
        when(creditCardRepository.findByOwnerId(testOwnerId)).thenReturn(cards);

        // Act
        List<CreditCardDTO> resultDTOs = creditCardService.getCardDTOsByOwnerId(testOwnerId);

        // Assert
        assertEquals(1, resultDTOs.size());
        CreditCardDTO dto = resultDTOs.get(0);
        assertEquals(testId, dto.getId());
        assertEquals(testLastFourDigits, dto.getLastFourDigits());
        assertEquals(testCardType, dto.getCardType());
        assertEquals(testLimit, dto.getLimit());
        assertEquals(testBalance, dto.getBalance());
        assertEquals(testStatus, dto.isStatus());
        verify(creditCardRepository).findByOwnerId(testOwnerId);
    }

    @Test
    public void testGetCardDTOById_WhenCardExists() {
        // Arrange
        when(creditCardRepository.findById(testId)).thenReturn(Optional.of(testCard));

        // Act
        Optional<CreditCardDTO> result = creditCardService.getCardDTOById(testId);

        // Assert
        assertTrue(result.isPresent());
        CreditCardDTO dto = result.get();
        assertEquals(testId, dto.getId());
        assertEquals(testLastFourDigits, dto.getLastFourDigits());
        assertEquals(testCardType, dto.getCardType());
        assertEquals(testLimit, dto.getLimit());
        assertEquals(testBalance, dto.getBalance());
        assertEquals(testStatus, dto.isStatus());
        verify(creditCardRepository).findById(testId);
    }

    @Test
    public void testGetCardDTOById_WhenCardDoesNotExist() {
        // Arrange
        when(creditCardRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        Optional<CreditCardDTO> result = creditCardService.getCardDTOById(testId);

        // Assert
        assertFalse(result.isPresent());
        verify(creditCardRepository).findById(testId);
    }
}