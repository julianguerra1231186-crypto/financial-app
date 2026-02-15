package com.julianguerra.financial_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad que representa un movimiento de fondos en el sistema.
 * Funciona como un registro inmutable (auditoría) de cada operación
 * financiera, permitiendo reconstruir el historial de saldos.
 */

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// Identificador único de la transacción para rastreo.

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;// Categoría de la operación: DEPOSIT, WITHDRAW o TRANSFER.

    private BigDecimal amount;// Valor monetario de la transacción. Se usa BigDecimal para precisión absoluta.

    private LocalDateTime transactionDate;// Instante exacto en que se registró la operación.

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;// Cuenta de origen (emisor). Puede ser nula en depósitos externos.

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;// Cuenta de destino (receptor). Puede ser nula en retiros físicos.
    /**
     * Asegura la integridad del registro temporal.
     * Antes de insertar en la base de datos, asigna la fecha y hora actual del sistema.
     */
    @PrePersist
    public void prePersist() {
        this.transactionDate = LocalDateTime.now();
    }
}
