/**
 * Esta clase representa errores específicos del dominio del negocio.
 * Se utiliza cuando una operación financiera no cumple con las reglas establecidas
 * (por ejemplo: saldo insuficiente, transferencia no permitida, etc.).
 * * Al heredar de RuntimeException, permite un manejo de errores más limpio
 * **sin obligar a declarar la excepción en cada firma de método.
 */

package com.julianguerra.financial_app.exception;

public class BusinessException extends RuntimeException {
    /**
     * Crea una nueva excepción con un mensaje descriptivo que ayude
     * al desarrollador (o al usuario final) a entender qué regla se rompió.
     *
     * *@param message El motivo detallado del error de negocio.
     */
    public BusinessException(String message) {
        super(message);
    }
}
