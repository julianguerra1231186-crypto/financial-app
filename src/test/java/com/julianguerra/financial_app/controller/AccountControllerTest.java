package com.julianguerra.financial_app.controller;

import com.julianguerra.financial_app.entity.Account;
import com.julianguerra.financial_app.entity.AccountType;
import com.julianguerra.financial_app.service.AccountService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void shouldCreateAccount() throws Exception {

        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.ZERO);

        when(accountService.createAccount(1L, AccountType.SAVINGS, true))
                .thenReturn(account);

        mockMvc.perform(post("/api/accounts")
                        .param("clientId", "1")
                        .param("accountType", "SAVINGS")
                        .param("exentaGMF", "true"))
                .andExpect(status().isOk());
    }
}
