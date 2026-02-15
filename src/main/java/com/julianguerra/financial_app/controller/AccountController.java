package com.julianguerra.financial_app.controller;

import com.julianguerra.financial_app.entity.Account;
import com.julianguerra.financial_app.entity.AccountType;
import com.julianguerra.financial_app.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controlador REST que expone los servicios relacionados con las cuentas bancarias.
 * Centraliza las peticiones externas para la apertura y gestión de productos financieros.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    /**
     * Punto de enlace para la creación de una nueva cuenta bancaria.
     * Recibe los parámetros necesarios a través de la URL (Query Params).
     * * @param clientId Identificador del cliente que será el titular de la cuenta.
     * @param *accountType Tipo de cuenta solicitada (ahorros o corriente).
     * @param *exentaGMF Bandera para determinar si la cuenta aplica para beneficios tributarios.
     * @return Respuesta exitosa con los datos de la cuenta recién generada.
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestParam Long clientId,
            @RequestParam AccountType accountType,
            @RequestParam Boolean exentaGMF
    ) {
        // Se delega la orquestación de la apertura al servicio especializado.
        return ResponseEntity.ok(
                accountService.createAccount(clientId, accountType, exentaGMF)
        );
    }
}
