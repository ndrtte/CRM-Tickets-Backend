package com.crm.gestiontickets.ticket.interfaces;

import com.crm.gestiontickets.ticket.dto.evento.TicketCreadoEvent;

public interface ITicketCreadoObserver {
    void enTicketCreado(TicketCreadoEvent event);
}
