/* Patrón: estructural: Facade, interactua con la base de datos */

package com.crm.gestiontickets.cliente.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.gestiontickets.cliente.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c " +
            "WHERE c.nombre LIKE %:valor% " +
            "   OR c.apellido LIKE %:valor% " +
            "   OR c.correo LIKE %:valor% " +
            "   OR c.celular LIKE %:valor% " +
            "   OR c.numeroIdentidad LIKE %:valor%")
    Page<Cliente> buscarPorCualquierCampo(String valor, Pageable pageable);
}

