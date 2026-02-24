package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.gestiontickets.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c " +
            "WHERE c.nombre LIKE %:valor% " +
            "   OR c.apellido LIKE %:valor% " +
            "   OR c.correo LIKE %:valor% " +
            "   OR c.celular LIKE %:valor% " +
            "   OR c.numeroIdentidad LIKE %:valor%")
    List<Cliente> buscarPorCualquierCampo(String valor);
}
