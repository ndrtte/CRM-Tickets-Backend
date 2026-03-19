package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;

public interface HistoricoTicketRepository extends JpaRepository<HistoricoTicket, Integer> {


    public HistoricoTicket findTopByTicketAndPasoOrigenOrderByIdHistoricoTicketsDesc(Ticket ticket, PasoFlujo paso);

    public List<HistoricoTicket> findHistoricoTicketByAgenteOrigen (Agente agente);
}
