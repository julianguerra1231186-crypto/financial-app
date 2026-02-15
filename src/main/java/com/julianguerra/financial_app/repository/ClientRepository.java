package com.julianguerra.financial_app.repository;

import com.julianguerra.financial_app.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     * Busca un cliente a través de su documento de identidad (Cédula, DNI, etc.).
     * Es crucial para validaciones de registro y procesos de autenticación únicos.
     * * @param identificationNumber Número de identificación único del cliente.
     * @return Un Optional que contendrá al cliente si existe en la base de datos.
     */
    Optional<Client> findByIdentificationNumber(String identificationNumber);
    /**
     * Recupera un cliente utilizando su dirección de correo electrónico.
     * Útil para procesos de recuperación de contraseña o comunicación directa.
     * * @param email Correo electrónico registrado.
     * @return Un Optional para manejar de forma segura la posibilidad de que no exista.
     */

    Optional<Client> findByEmail(String email);
}
