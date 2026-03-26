/*Patron:  Arquitectonico, encapsula CRUD de datos desacopla la logica de negocios de lapersistencia*/

package com.crm.gestiontickets.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.ticket.entity.EstadoTicket;

public interface EstadoTicketRepository extends JpaRepository<EstadoTicket, Integer>{
    
    public EstadoTicket findByEstadoTicket(String estadoTicket);

}
