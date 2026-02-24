package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Agente;

public interface AgentesRepository extends JpaRepository<Agente, Integer>{
    
}
