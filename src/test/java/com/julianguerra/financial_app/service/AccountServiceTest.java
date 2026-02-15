package com.julianguerra.financial_app.service;

import com.julianguerra.financial_app.entity.*;
import com.julianguerra.financial_app.exception.BusinessException;
import com.julianguerra.financial_app.repository.AccountRepository;
import com.julianguerra.financial_app.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private AccountService accountService;

    private Client client;

    @BeforeEach
    void setup() {
        client = new Client();
        client.setId(1L);
    }

    @Test
    void shouldCreateAccountSuccessfully() {

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(accountRepository.save(any(Account.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Account account = accountService.createAccount(1L, AccountType.SAVINGS, true);

        assertNotNull(account);
        assertEquals(AccountType.SAVINGS, account.getAccountType());
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenClientNotFound() {

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () ->
                accountService.createAccount(1L, AccountType.SAVINGS, true)
        );
    }
}
