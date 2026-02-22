package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.EstadosTicket;

public interface EstadosTicketRepository extends JpaRepository<EstadosTicket, Integer>{
    
}
