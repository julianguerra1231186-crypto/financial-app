package com.julianguerra.financial_app.controller;

import com.julianguerra.financial_app.entity.Transaction;
import com.julianguerra.financial_app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount
    ) {
        return ResponseEntity.ok(
                transactionService.deposit(accountId, amount)
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount
    ) {
        return ResponseEntity.ok(
                transactionService.withdraw(accountId, amount)
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(
            @RequestParam Long sourceAccountId,
            @RequestParam Long destinationAccountId,
            @RequestParam BigDecimal amount
    ) {
        return ResponseEntity.ok(
                transactionService.transfer(sourceAccountId, destinationAccountId, amount)
        );
    }
}
