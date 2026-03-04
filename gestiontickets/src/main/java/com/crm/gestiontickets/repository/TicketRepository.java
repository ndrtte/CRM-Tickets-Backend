package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    
    public List<Ticket> findByCliente(Cliente cliente);

}
