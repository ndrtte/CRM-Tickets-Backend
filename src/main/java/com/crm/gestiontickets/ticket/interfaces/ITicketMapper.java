package com.crm.gestiontickets.ticket.interfaces;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;

public interface ITicketMapper {

    TicketDetalle mapearTicketADetalle(Ticket ticket);
    
    Page<TicketEtapaAgenteDetalle> mapearTicketsEnProceso(Agente agente, Pageable pageable, FiltroFechaTicketEnum fechaOp, LocalDate fecha);

    Page<TicketEtapaAgenteDetalle> mapearTicketsFinalizados(Agente agente, Pageable pageable, FiltroFechaTicketEnum fechaOp, LocalDate fecha);

    Page<TicketEtapaAgenteDetalle> mapearTicketsTodos(Agente agente, Pageable pageable, FiltroFechaTicketEnum fechaOp, LocalDate fecha);

}
