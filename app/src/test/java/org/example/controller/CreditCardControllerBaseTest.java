package org.example.controller;

import org.example.model.CreditCard;
import org.example.service.CreditCardService;
import org.example.service.TransactionService;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

/**
 * Test class for the CreditCardController's base controller functionality.
 * This extends BaseControllerTest to test the CRUD operations inherited from BaseController.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreditCardControllerBaseTest extends BaseControllerTest<CreditCard, Long, CreditCardService, CreditCardController> {

    @Mock
    private TransactionService mockTransactionService;
    
    @Override
    protected CreditCardController createController() {
        setupServiceMocks();
        return new CreditCardController(mockService, mockTransactionService);
    }

    @Override
    protected CreditCard createEntity() {
        CreditCard card = new CreditCard();
        card.setId(1L);
        card.setOwnerId("user123");
        card.setLastFourDigits("3456");
        card.setCardName("John Doe");
        card.setValidUntil("12/25");
        card.setCardType("VISA");
        card.setStatus(true);
        card.setLimit(new BigDecimal("5000.00"));
        card.setBalance(new BigDecimal("1500.00"));
        return card;
    }

    @Override
    protected Long createId() {
        return 1L;
    }

    @Override
    protected void setupServiceMocks() {
        // Create a proper mock of CreditCardService instead of relying on the generic mock
        CreditCardService creditCardService = org.mockito.Mockito.mock(CreditCardService.class);
        mockService = creditCardService;
    }
}