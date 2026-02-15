package com.julianguerra.financial_app.repository;

import com.julianguerra.financial_app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repositorio de persistencia para la entidad Transaction.
 * Sirve como el diario contable del sistema, permitiendo registrar y
 * consultar cada movimiento de dinero realizado entre cuentas.
 * * Al extender de JpaRepository, hereda automáticamente las operaciones
 * de guardado, búsqueda y auditoría de transacciones.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
