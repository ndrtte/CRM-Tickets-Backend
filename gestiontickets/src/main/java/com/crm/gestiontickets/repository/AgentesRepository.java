package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Agentes;

public interface AgentesRepository extends JpaRepository<Agentes, Integer>{
    
}
