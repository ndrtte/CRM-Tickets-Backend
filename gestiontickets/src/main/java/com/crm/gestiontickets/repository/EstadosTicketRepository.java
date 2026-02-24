package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.EstadoTicket;

public interface EstadosTicketRepository extends JpaRepository<EstadoTicket, Integer>{
    
}
