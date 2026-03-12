package com.crm.gestiontickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.IdPasoFlujo;
import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.repository.TicketRepository;

@Service
public class PasoFlujoService {

    @Autowired
    private TicketRepository ticketRepository;

    public Respuesta<IdPasoFlujo> obtenerPasoActual(String idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).get();

        Integer idPasoFlujo = ticket.getPasoActual() != null ? ticket.getPasoActual().getIdPasosFlujo() : null;

        if (idPasoFlujo == null) {
            return new Respuesta<>(false, "El ticket no tiene un paso de flujo asignado.", null);
        }

        return new Respuesta<>(true, "Paso de flujo actual recuperado correctamente.", new IdPasoFlujo(idPasoFlujo));
    }

}
