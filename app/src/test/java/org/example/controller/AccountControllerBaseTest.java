package org.example.controller;

import org.example.model.Account;
import org.example.service.AccountService;
import org.example.service.AccountTransactionService;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Test class for the AccountController's base controller functionality.
 * This extends BaseControllerTest to test the CRUD operations inherited from BaseController.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountControllerBaseTest extends BaseControllerTest<Account, Long, AccountService, AccountController> {

    @Mock
    private AccountTransactionService mockAccountTransactionService;
    
    @Override
    protected AccountController createController() {
        setupServiceMocks();
        return new AccountController(mockService, mockAccountTransactionService);
    }

    @Override
    protected Account createEntity() {
        Account account = new Account();
        account.setId(1L);
        account.setOwnerId("user123");
        account.setAccountNumber("ACC123456");
        account.setAccountType("CHECKING");
        account.setBalance(new BigDecimal("1000.00"));
        account.setCreationDate(LocalDateTime.now());
        return account;
    }

    @Override
    protected Long createId() {
        return 1L;
    }

    @Override
    protected void setupServiceMocks() {
        // Create a proper mock of AccountService instead of relying on the generic mock
        AccountService accountService = org.mockito.Mockito.mock(AccountService.class);
        mockService = accountService;
    }
}