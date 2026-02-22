package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Tickets;

public interface TicketRepository extends JpaRepository<Tickets, String> {
    
}
