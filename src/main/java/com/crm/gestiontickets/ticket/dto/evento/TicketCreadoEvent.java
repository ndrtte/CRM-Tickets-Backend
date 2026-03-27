package com.crm.gestiontickets.ticket.dto.evento;

import org.springframework.stereotype.Component;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.dto.TicketCreacion;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TicketCreadoEvent {
    
    private Ticket ticket;
    private TicketCreacion dto;
    private PasoFlujo pasoAnterior;
    private PasoFlujo pasoNuevo;
    private Agente agenteOrigen;

}
