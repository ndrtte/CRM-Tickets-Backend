package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;

public interface HistoricoTicketRepository extends JpaRepository<HistoricoTicket, Integer> {

    public boolean existsByTicketAndPasoDestino(Ticket ticket, PasoFlujo paso);

    @Query("""
    SELECT h 
    FROM HistoricoTicket h
    WHERE h.ticket.idTicket = :idTicket
    AND (:idPaso = h.pasoDestino.idPasosFlujo OR :idPaso = h.pasoOrigen.idPasosFlujo)
    ORDER BY h.idHistoricoTickets DESC""")
    List<HistoricoTicket> findHistoricoTicketByTicketYEtapa(String idTicket, Integer idPaso);

}
