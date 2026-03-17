package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.Nota;

public interface NotaRepository extends JpaRepository<Nota, Integer> {

    @Query("""
    SELECT h
    FROM HistoricoTicket h
    WHERE h.ticket.idTicket = :idTicket
    AND h.pasoDestino.idPasosFlujo = :idPaso
    ORDER BY h.idHistoricoTickets DESC
    """)
    List<HistoricoTicket> findLlegadaPaso(String idTicket, Integer idPaso);

    @Query("""
    SELECT h
    FROM HistoricoTicket h
    WHERE h.ticket.idTicket = :idTicket
      AND h.pasoOrigen.idPasosFlujo = :idPaso
      AND h.pasoDestino IS NULL
    ORDER BY h.idHistoricoTickets DESC
    """)
    List<HistoricoTicket> findCierrePaso(String idTicket, Integer idPaso);

}
