package com.crm.gestiontickets.ticket.interfaces;

import com.crm.gestiontickets.agente.dto.IdAgente;
import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.IdTicket;

//Interface Segregation
public interface ITicketAgenteService {

    Respuesta<IdTicket> asignarAgenteATicket(String idTicket, IdAgente idAgente);
    
}
