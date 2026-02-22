package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Clientes;

public interface ClientesRepository extends JpaRepository<Clientes, Integer>{
    
}
