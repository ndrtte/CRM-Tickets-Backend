/*Patron:  Arquitectonico, encapsula CRUD de datos desacopla la logica de negocios de lapersistencia*/

package com.crm.gestiontickets.ticket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;

public interface HistoricoTicketRepository extends JpaRepository<HistoricoTicket, Integer> {

    public boolean existsByTicketAndPasoOrigen(Ticket ticket, PasoFlujo paso);

    public HistoricoTicket findTopByTicketAndPasoOrigenOrderByIdHistoricoTicketsDesc(Ticket ticket, PasoFlujo paso);

    public HistoricoTicket findTopByTicketOrderByFechaHistoricoDesc(Ticket ticket);
    public List<HistoricoTicket> findHistoricoTicketByAgenteOrigen (Agente agente);

        @Query("""
SELECT h FROM HistoricoTicket h
WHERE h.agenteDestino.idAgente = :idAgente
  AND (:estadoTicket IS NULL OR h.ticket.estado.estadoTicket = :estadoTicket)
  AND (:fechaInicio IS NULL OR h.fechaHistorico >= :fechaInicio)
  AND (:fechaFin IS NULL OR h.fechaHistorico <= :fechaFin)
""")
Page<HistoricoTicket> filtrarPorAgente(
    @Param("idAgente") Integer idAgente,
    @Param("estadoTicket") String estadoTicket,
    @Param("fechaInicio") LocalDateTime fechaInicio,
    @Param("fechaFin") LocalDateTime fechaFin,
    org.springframework.data.domain.Pageable pageable
);

        public Page<HistoricoTicket> filtrarHistorico(Integer idDepartamento, String estadoTicket, Integer idAgente,
                LocalDateTime fechaInicio, LocalDateTime fechaFin, org.springframework.data.domain.Pageable pageable);

}


