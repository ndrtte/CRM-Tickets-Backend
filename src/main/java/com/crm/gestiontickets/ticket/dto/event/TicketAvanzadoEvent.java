package com.crm.gestiontickets.ticket.dto.event;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TicketAvanzadoEvent {
    Ticket ticket;
    Agente agenteOrigen; 
    PasoFlujo pasoAnterior; 
    String nota;
}
