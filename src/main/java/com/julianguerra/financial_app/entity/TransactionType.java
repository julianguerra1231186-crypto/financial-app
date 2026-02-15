package com.julianguerra.financial_app.entity;
/**
 * Clasificación de los movimientos financieros permitidos en el sistema.
 * Determina el flujo del dinero (entrada, salida o movimiento entre cuentas)
 * y las reglas de validación asociadas a cada operación.
 */
public enum TransactionType {
    /**
     * Depósito / Consignación: Representa una entrada de dinero a una cuenta específica.
     * Generalmente no requiere una cuenta de origen.
     */
    DEPOSIT,
    /**
     * Retiro: Representa una salida de capital de una cuenta.
     * Requiere validación previa de saldo suficiente y estado de cuenta activo.
     */
    WITHDRAW,
    /**
     * Transferencia: Representa un movimiento de fondos entre dos cuentas del sistema.
     * Involucra tanto una cuenta de origen como una de destino.
     */
    TRANSFER
}
