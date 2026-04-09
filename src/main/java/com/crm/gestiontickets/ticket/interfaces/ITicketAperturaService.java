package com.crm.gestiontickets.ticket.interfaces;

import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.TicketApertura;
import com.crm.gestiontickets.ticket.dto.TicketCreacion;
import com.crm.gestiontickets.ticket.dto.TicketPasoResponse;

//Interface Segregation
public interface ITicketAperturaService {

    Respuesta<TicketPasoResponse> aperturaTicket(TicketApertura dto);

    Respuesta<TicketPasoResponse> crearTicket(TicketCreacion dto);

}
