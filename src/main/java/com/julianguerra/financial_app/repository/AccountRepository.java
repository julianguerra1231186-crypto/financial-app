package com.julianguerra.financial_app.repository;

import com.julianguerra.financial_app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de persistencia para la entidad Account.
 * Proporciona el mecanismo de comunicación con la base de datos para gestionar
 * el ciclo de vida de las cuentas bancarias.
 */

public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Recupera una cuenta específica utilizando su número de cuenta único.
     * @param *accountNumber El número de cuenta de 10 dígitos a buscar.
     * @return Un Optional que contiene la cuenta si es hallada, evitando retornos nulos.
     */

    Optional<Account> findByAccountNumber(String accountNumber);
    /**
     * Verifica la existencia de una cuenta en el sistema mediante su número.
     * Utilizado principalmente para validaciones de unicidad antes de crear nuevos registros.
     * @param *accountNumber El número de cuenta a validar.
     * @return true si el número ya está registrado, false en caso contrario.
     */

    boolean existsByAccountNumber(String accountNumber);
}
