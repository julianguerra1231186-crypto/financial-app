package com.julianguerra.financial_app.service;

import com.julianguerra.financial_app.entity.*;
import com.julianguerra.financial_app.exception.BusinessException;
import com.julianguerra.financial_app.repository.AccountRepository;
import com.julianguerra.financial_app.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Servicio encargado de la gestión y lógica operativa de las cuentas bancarias.
 * Maneja el ciclo de vida de los productos financieros, desde su apertura
 * hasta la asignación de identificadores únicos.
 */

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    /**
     * Realiza la apertura de una nueva cuenta para un cliente existente.
     * * @param clientId Identificador del cliente que será titular de la cuenta.
     * @param *accountType Tipo de cuenta a crear (Ahorros o Corriente).
     * @param *exentaGMF Indica si aplica la exención del impuesto bancario.
     * @return La cuenta creada y persistida en el sistema.
     * @throws *BusinessException Si el cliente no existe en la base de datos.
     */
    public Account createAccount(Long clientId, AccountType accountType, Boolean exentaGMF) {
// Se verifica que el cliente exista antes de intentar asociarle una cuenta.
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new BusinessException("Client not found"));
// Inicialización de la nueva cuenta con valores por defecto y estado activo.
        Account account = new Account();
        account.setAccountType(accountType);
        account.setExentaGMF(exentaGMF);
        account.setClient(client);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.ZERO);
// Generación y asignación del número de cuenta único basado en reglas de negocio.
        String accountNumber = generateAccountNumber(accountType);
        account.setAccountNumber(accountNumber);

        return accountRepository.save(account);
    }
    /**
     * Genera un número de cuenta único de 10 dígitos basándose en el tipo de producto.
     * Las cuentas de Ahorros inician con '53' y las Corrientes con '33'.
     * * @param accountType El tipo de cuenta para definir el prefijo.
     * @return Un número de cuenta de 10 dígitos que no existe previamente.
     */
    private String generateAccountNumber(AccountType accountType) {

        String prefix;
// Selección del prefijo reglamentario según el producto.
        if (accountType == AccountType.SAVINGS) {
            prefix = "53";
        } else if (accountType == AccountType.CURRENT) {
            prefix = "33";
        } else {
            throw new BusinessException("Invalid account type");
        }

        String number;
        Random random = new Random();
// Ciclo de generación aleatoria para garantizar que el número sea único en la base de datos.
        do {
            int randomNumber = 10000000 + random.nextInt(90000000);
            number = prefix + randomNumber;
        } while (accountRepository.existsByAccountNumber(number));

        return number;
    }
}
