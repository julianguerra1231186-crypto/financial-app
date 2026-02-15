package com.julianguerra.financial_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
/**
 * Gestor centralizado de errores para toda la aplicación.
 * Esta clase intercepta las excepciones que ocurren en cualquier controlador
 * y las transforma en respuestas JSON amigables y consistentes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Captura específicamente los errores de lógica de negocio (BusinessException).
     * En lugar de mostrar un error interno genérico, devolvemos un código 400 (Bad Request)
     * indicando exactamente qué regla financiera se ha incumplido.
     *
     * @param *ex La excepción de negocio capturada.
     * @return Una respuesta estructurada con la hora exacta y el mensaje de error.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        // Construimos un mapa con información útil para que el frontend pueda
        // informar correctamente al usuario sobre lo sucedido.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", ex.getMessage()
                )
        );
    }
}
