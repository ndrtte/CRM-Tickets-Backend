package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.HistoricoTickets;

public interface HistoricoTicketsRepository extends JpaRepository<HistoricoTickets, Integer> {
    
}
