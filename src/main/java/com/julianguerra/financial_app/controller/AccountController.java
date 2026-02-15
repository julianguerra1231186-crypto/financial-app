package com.julianguerra.financial_app.controller;

import com.julianguerra.financial_app.entity.Account;
import com.julianguerra.financial_app.entity.AccountStatus;
import com.julianguerra.financial_app.entity.AccountType;
import com.julianguerra.financial_app.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestParam Long clientId,
            @RequestParam AccountType accountType,
            @RequestParam Boolean exentaGMF
    ) {
        return ResponseEntity.ok(
                accountService.createAccount(clientId, accountType, exentaGMF)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Account> updateAccountStatus(
            @PathVariable Long id,
            @RequestParam AccountStatus status
    ) {
        return ResponseEntity.ok(
                accountService.updateAccountStatus(id, status)
        );
    }
}
