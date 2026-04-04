/*Patron:  Arquitectonico, encapsula CRUD de datos desacopla la logica de negocios de lapersistencia*/
package com.crm.gestiontickets.ticket.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;

public interface HistoricoTicketRepository extends JpaRepository<HistoricoTicket, Integer> {

    public boolean existsByTicketAndPasoOrigen(Ticket ticket, PasoFlujo paso);

    public HistoricoTicket findTopByTicketAndPasoOrigenOrderByIdHistoricoTicketsDesc(Ticket ticket, PasoFlujo paso);

    public HistoricoTicket findTopByTicketOrderByFechaHistoricoDesc(Ticket ticket);

    @Query("SELECT h FROM HistoricoTicket h " +
            "WHERE h.agenteOrigen = :agente " +
            "AND h.pasoDestino IS NOT NULL " +
            "AND (:fecha IS NULL OR " +
            "(:fechaOp = 'MENOR' AND h.ticket.fechaCreacion < :fecha) OR " +
            "(:fechaOp = 'IGUAL' AND h.ticket.fechaCreacion = :fecha) OR " +
            "(:fechaOp = 'MAYOR' AND h.ticket.fechaCreacion > :fecha))")
    Page<HistoricoTicket> findHistoricoTicketByAgenteOrigen(Agente agente, FiltroFechaTicketEnum fechaOp, LocalDate fecha, Pageable pageable);
}
