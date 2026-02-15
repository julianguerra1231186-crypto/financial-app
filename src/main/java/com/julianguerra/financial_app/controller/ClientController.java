package com.julianguerra.financial_app.controller;

import com.julianguerra.financial_app.entity.Client;
import com.julianguerra.financial_app.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controlador REST para la gestión de Clientes.
 * Expone los puntos de entrada (endpoints) para que aplicaciones externas
 * o el frontend puedan interactuar con la lógica financiera de clientes.
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    /**
     * Endpoint para el registro de un nuevo cliente.
     * Recibe la información en formato JSON, la valida a través del servicio
     * y retorna el objeto creado con su estado correspondiente.
     *
     * @param *client Objeto con los datos del nuevo cliente.
     * @return ResponseEntity con el cliente creado y un estado HTTP 200 (OK).
     */

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        // Delegamos la creación al servicio de negocio y envolvemos el resultado en una respuesta HTTP.
        return ResponseEntity.ok(clientService.createClient(client));
    }
}
