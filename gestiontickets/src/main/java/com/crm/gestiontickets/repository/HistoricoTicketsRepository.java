package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.HistoricoTicket;

public interface HistoricoTicketsRepository extends JpaRepository<HistoricoTicket, Integer> {
    
}
