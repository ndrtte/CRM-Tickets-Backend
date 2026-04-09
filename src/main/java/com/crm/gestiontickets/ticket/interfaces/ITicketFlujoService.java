package com.crm.gestiontickets.ticket.interfaces;

import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.TicketAvanzarEtapa;
import com.crm.gestiontickets.ticket.dto.TicketPasoResponse;

//Interface Segregation
public interface ITicketFlujoService {
    
    Respuesta<TicketPasoResponse> avanzarEtapa(TicketAvanzarEtapa ticketNvoEtapa);

    Respuesta<TicketPasoResponse> cerrarTicket(TicketAvanzarEtapa ticketNvoEtapa);
}
