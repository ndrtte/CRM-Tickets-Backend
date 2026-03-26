package com.crm.gestiontickets.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;

public interface HistoricoTicketRepository extends JpaRepository<HistoricoTicket, Integer> {

    public boolean existsByTicketAndPasoOrigen(Ticket ticket, PasoFlujo paso);

    public HistoricoTicket findTopByTicketAndPasoOrigenOrderByIdHistoricoTicketsDesc(Ticket ticket, PasoFlujo paso);

    public HistoricoTicket findTopByTicketOrderByFechaHistoricoDesc(Ticket ticket);
    public List<HistoricoTicket> findHistoricoTicketByAgenteOrigen (Agente agente);
}
