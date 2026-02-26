package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    
}
