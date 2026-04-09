package com.crm.gestiontickets.ticket.service.strategy;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.dto.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;
import com.crm.gestiontickets.ticket.interfaces.FiltroTicketsAgenteStrategy;
import com.crm.gestiontickets.ticket.mapper.TicketMapper;

@Component
public class FiltroTodos implements FiltroTicketsAgenteStrategy {

    @Autowired
    private TicketMapper ticketMapper;

    //Liskov
    @Override
    public Page<TicketEtapaAgenteDetalle> aplicar(
            Agente agente,
            Pageable pageable,
            FiltroFechaTicketEnum fechaOp,
            LocalDate fecha) {

        return ticketMapper.mapearTicketsTodos(agente, pageable, fechaOp, fecha);
    }
}