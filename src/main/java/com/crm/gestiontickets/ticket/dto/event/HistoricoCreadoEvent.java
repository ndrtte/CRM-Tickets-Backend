package com.crm.gestiontickets.ticket.dto.event;

import com.crm.gestiontickets.ticket.entity.HistoricoTicket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HistoricoCreadoEvent {
    private HistoricoTicket historico;
    private String nota;
}
