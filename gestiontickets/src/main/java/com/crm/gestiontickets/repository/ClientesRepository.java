package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.gestiontickets.entity.Cliente;

public interface ClientesRepository extends JpaRepository<Cliente, Integer>{
    
       @Query("SELECT c FROM Cliente c WHERE " +
           "c.primerNombre LIKE %:valor% OR " +
           "c.segundoNombre LIKE %:valor% OR " +
           "c.primerApellido LIKE %:valor% OR " +
           "c.segundoApellido LIKE %:valor% OR " +
           "c.celular LIKE %:valor% OR " +
           "c.numIdentificacion LIKE %:valor%")
    List<Cliente> buscarPorCualquierCampo(@Param("valor") String valor);

}
