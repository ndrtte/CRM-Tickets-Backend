package com.crm.gestiontickets.ticket.interfaces;

import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.enums.EstadoEtapaTicketEnum;

public interface IEstadoEtapaService {
    EstadoEtapaTicketEnum obtenerEstado(Ticket ticket, PasoFlujo paso, PasoFlujo pasoActual, boolean ticketCerrado);
}
