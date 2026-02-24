package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.HistoricoTicket;

public interface HistoricoTicketRepository extends JpaRepository<HistoricoTicket, Integer> {
    
}
