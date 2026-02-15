package com.julianguerra.financial_app.service;

import com.julianguerra.financial_app.entity.*;
import com.julianguerra.financial_app.exception.BusinessException;
import com.julianguerra.financial_app.repository.AccountRepository;
import com.julianguerra.financial_app.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
/**
 * Motor transaccional del sistema financiero.
 * Gestiona el movimiento de capital garantizando que los saldos siempre cuadren
 * y que cada operación quede registrada para auditoría.
 */

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    /**
     * Consignación (Depósito)
     */
    @Transactional
    public Transaction deposit(Long accountId, BigDecimal amount) {
        // Se localiza la cuenta receptora o se reporta el error si no existe.
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessException("Account not found"));
        // Validación de seguridad: No se permiten depósitos negativos o en cero.
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Amount must be greater than zero");
        }
        // Actualización contable: Se suma el monto al saldo actual.
        account.setBalance(account.getBalance().add(amount));
        // Construcción del comprobante de transacción.
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .amount(amount)
                .destinationAccount(account)
                .build();
        // Persistencia de los cambios en la cuenta y el registro de la transacción.
        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }

    /**
     * Retiro
     */
    /**
     * Procesa el retiro de efectivo de una cuenta.
     * Verifica que existan fondos suficientes antes de proceder.
     */
    @Transactional
    public Transaction withdraw(Long accountId, BigDecimal amount) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessException("Account not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Amount must be greater than zero");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.WITHDRAW)
                .amount(amount)
                .sourceAccount(account)
                .build();

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }

    /**
     * Transferencia
     */
    @Transactional
    public Transaction transfer(Long sourceAccountId, Long destinationAccountId, BigDecimal amount) {

        if (sourceAccountId.equals(destinationAccountId)) {
            throw new BusinessException("Cannot transfer to the same account");
        }

        Account source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new BusinessException("Source account not found"));

        Account destination = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new BusinessException("Destination account not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Amount must be greater than zero");
        }

        if (source.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Insufficient balance");
        }

        source.setBalance(source.getBalance().subtract(amount));
        destination.setBalance(destination.getBalance().add(amount));

        accountRepository.save(source);
        accountRepository.save(destination);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.TRANSFER)
                .amount(amount)
                .sourceAccount(source)
                .destinationAccount(destination)
                .build();

        return transactionRepository.save(transaction);
    }
}
