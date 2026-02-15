package com.julianguerra.financial_app.service;

import com.julianguerra.financial_app.entity.Client;
import com.julianguerra.financial_app.exception.BusinessException;
import com.julianguerra.financial_app.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

/**
 * Servicio encargado de la lógica de negocio para la gestión de clientes.
 * Asegura que cada nuevo registro cumpla con las políticas legales
 * y de integridad de la plataforma financiera.
 */
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    /**
     * Registra un nuevo cliente en el sistema tras superar una serie de validaciones.
     * * El proceso sigue este flujo:
     * 1. Verificación de mayoría de edad.
     * 2. Comprobación de identidad única (DNI/Cédula).
     * 3. Validación de correo electrónico no duplicado.
     *
     * @param *client Datos del cliente a registrar.
     * @return El cliente guardado con su ID generado.
     * @throws *BusinessException Si alguna regla de negocio es vulnerada.
     */
    public Client createClient(Client client) {
// --- 1. Validación de Política de Edad ---
        if (Period.between(client.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new BusinessException("Client must be over 18 years old");
        }
// --- 2. Validación de Identidad Única ---
        // Evitamos que una misma persona se registre varias veces con el mismo documento.
        clientRepository.findByIdentificationNumber(client.getIdentificationNumber())
                .ifPresent(c -> {
                    throw new BusinessException("Client already exists with this identification");
                });
// --- 3. Validación de Contacto ---
        // El correo electrónico debe ser único para garantizar la seguridad de las notificaciones.
        clientRepository.findByEmail(client.getEmail())
                .ifPresent(c -> {
                    throw new BusinessException("Email already registered");
                });
// Una vez superados los filtros, procedemos con el guardado persistente.
        return clientRepository.save(client);
    }
}
