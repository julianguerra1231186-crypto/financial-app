package com.julianguerra.financial_app.service;

import com.julianguerra.financial_app.entity.*;
import com.julianguerra.financial_app.exception.BusinessException;
import com.julianguerra.financial_app.repository.AccountRepository;
import com.julianguerra.financial_app.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * Servicio encargado de la gestión y lógica operativa de las cuentas bancarias.
 * Maneja la creación, consulta y actualización de estado de los productos financieros.
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    /**
     * Realiza la apertura de una nueva cuenta para un cliente existente.
     */
    public Account createAccount(Long clientId, AccountType accountType, Boolean exentaGMF) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new BusinessException("Client not found"));

        Account account = new Account();
        account.setAccountType(accountType);
        account.setExentaGMF(exentaGMF);
        account.setClient(client);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.ZERO);

        String accountNumber = generateAccountNumber(accountType);
        account.setAccountNumber(accountNumber);

        return accountRepository.save(account);
    }

    /**
     * Obtiene una cuenta por ID.
     */
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessException("Account not found"));
    }

    /**
     * Obtiene todas las cuentas registradas.
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Permite actualizar el estado de la cuenta.
     * No se puede cancelar si el saldo es mayor a 0.
     */
    public Account updateAccountStatus(Long accountId, AccountStatus newStatus) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessException("Account not found"));

        if (newStatus == AccountStatus.CANCELLED &&
                account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessException("Cannot cancel account with balance greater than zero");
        }

        account.setStatus(newStatus);

        return accountRepository.save(account);
    }

    /**
     * Genera un número de cuenta único de 10 dígitos.
     * Ahorros → 53
     * Corriente → 33
     */
    private String generateAccountNumber(AccountType accountType) {

        String prefix;

        if (accountType == AccountType.SAVINGS) {
            prefix = "53";
        } else if (accountType == AccountType.CURRENT) {
            prefix = "33";
        } else {
            throw new BusinessException("Invalid account type");
        }

        String number;
        Random random = new Random();

        do {
            int randomNumber = 10000000 + random.nextInt(90000000);
            number = prefix + randomNumber;
        } while (accountRepository.existsByAccountNumber(number));

        return number;
    }
}
