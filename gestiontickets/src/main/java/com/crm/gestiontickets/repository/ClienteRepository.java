package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.gestiontickets.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c " +
            "WHERE c.primerNombre LIKE %:valor% " +
            "   OR c.segundoNombre LIKE %:valor% " +
            "   OR c.primerApellido LIKE %:valor% " +
            "   OR c.segundoApellido LIKE %:valor% " +
            "   OR c.celular LIKE %:valor% " +
            "   OR c.correo LIKE %:valor% " +
            "   OR c.numIdentidad LIKE %:valor%")
    List<Cliente> buscarPorCualquierCampo(String valor);
}
