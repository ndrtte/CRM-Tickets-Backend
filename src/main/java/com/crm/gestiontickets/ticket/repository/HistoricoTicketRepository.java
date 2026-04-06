/*Patron:  Arquitectonico, encapsula CRUD de datos desacopla la logica de negocios de lapersistencia*/
package com.crm.gestiontickets.ticket.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;

public interface HistoricoTicketRepository extends JpaRepository<HistoricoTicket, Integer> {

    public boolean existsByTicketAndPasoOrigen(Ticket ticket, PasoFlujo paso);

    public HistoricoTicket findTopByTicketAndPasoOrigenOrderByIdHistoricoTicketsDesc(Ticket ticket, PasoFlujo paso);

    public HistoricoTicket findTopByTicketOrderByFechaHistoricoDesc(Ticket ticket);

    @Query("SELECT h FROM HistoricoTicket h " +
            "WHERE h.agenteOrigen = :agente " +
            "AND h.pasoDestino IS NOT NULL " +
            "AND (:inicio IS NULL OR h.ticket.fechaCreacion >= :inicio) " +
            "AND (:fin IS NULL OR h.ticket.fechaCreacion < :fin)")
    Page<HistoricoTicket> findHistoricoTicketByAgenteOrigen(
            Agente agente,
            LocalDateTime inicio,
            LocalDateTime fin,
            Pageable pageable);
}
