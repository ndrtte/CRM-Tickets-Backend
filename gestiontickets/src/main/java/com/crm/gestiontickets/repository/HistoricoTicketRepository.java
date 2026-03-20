package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;

public interface HistoricoTicketRepository extends JpaRepository<HistoricoTicket, Integer> {

    public boolean existsByTicketAndPasoOrigen(Ticket ticket, PasoFlujo paso);

    public HistoricoTicket findTopByTicketAndPasoOrigenOrderByIdHistoricoTicketsDesc(Ticket ticket, PasoFlujo paso);

    public HistoricoTicket findTopByTicketOrderByFechaHistoricoDesc(Ticket ticket);
}
