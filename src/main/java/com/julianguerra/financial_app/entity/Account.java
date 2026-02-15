package com.julianguerra.financial_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa una cuenta financiera en el sistema.
 * Gestiona el saldo, el estado legal y la vinculación con el titular.
 */

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// Identificador interno único y autoincremental.

    @Enumerated(EnumType.STRING)
    private AccountType accountType;// Categoría de la cuenta (ej. Ahorros, Corriente).

    @Column(unique = true, nullable = false, length = 10)
    private String accountNumber; // Número de cuenta único de 10 dígitos.

    @Enumerated(EnumType.STRING)
    private AccountStatus status;// Estado operativo actual de la cuenta.

    private BigDecimal balance;// Saldo disponible con precisión decimal financiera.

    private Boolean exentaGMF;// Indica si la cuenta está marcada como exenta del impuesto GMF.

    private LocalDate createdAt;// Fecha de creación del registro para auditoría.

    private LocalDate updatedAt;// Fecha de la última modificación realizada.

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    /**
     * Configuración de valores iniciales antes de la persistencia.
     * Establece la fecha actual, el estado activo y garantiza que el saldo no sea nulo.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
        this.status = AccountStatus.ACTIVE;
        if (this.balance == null) {
            this.balance = BigDecimal.ZERO;
        }
    }
    /**
     * Actualiza automáticamente la fecha de modificación antes de cualquier cambio.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
