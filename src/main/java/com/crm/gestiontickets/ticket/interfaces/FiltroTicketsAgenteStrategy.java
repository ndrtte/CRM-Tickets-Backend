package com.crm.gestiontickets.ticket.interfaces;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.dto.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;

public interface FiltroTicketsAgenteStrategy {

    Page<TicketEtapaAgenteDetalle> aplicar(
        Agente agente, 
        Pageable pageable, 
        FiltroFechaTicketEnum fechaOp,
        LocalDate fecha);

}
