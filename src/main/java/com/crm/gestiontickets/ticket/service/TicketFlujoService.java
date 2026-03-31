/*Patron: comportamiento: State, administra la transicion de un ticket entre etapas */
package com.crm.gestiontickets.ticket.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.TicketAvanzarEtapa;
import com.crm.gestiontickets.ticket.dto.TicketPasoResponse;
import com.crm.gestiontickets.ticket.dto.event.TicketAvanzadoEvent;
import com.crm.gestiontickets.ticket.entity.EstadoTicket;
import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.repository.EstadoTicketRepository;
import com.crm.gestiontickets.ticket.repository.PasoFlujoRepository;
import com.crm.gestiontickets.ticket.repository.TicketRepository;

@Service
public class TicketFlujoService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PasoFlujoRepository pasoFlujoRepository;

    @Autowired
    private EstadoTicketRepository estadoTicketRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public Respuesta<TicketPasoResponse> avanzarEtapa(TicketAvanzarEtapa ticketNvoEtapa) {
        Ticket ticket = ticketRepository.findById(ticketNvoEtapa.getIdTicket()).get();

        PasoFlujo pasoActual = ticket.getPasoActual();
        PasoFlujo pasoAnterior = pasoActual;

        Flujo flujo = pasoActual.getIdFlujo();

        Integer siguienteOrden = pasoActual.getOrden() + 1;

        PasoFlujo siguientePaso = pasoFlujoRepository.findByIdFlujoAndOrden(flujo, siguienteOrden);

        if (siguientePaso == null) {
            return cerrarTicket(ticketNvoEtapa);
        }

        Agente agenteOrigen = ticket.getAgenteAsignado();

        ticket.setPasoActual(siguientePaso);
        ticket.setAgenteAsignado(null);
        ticket.setFechaActualizacion(LocalDateTime.now());

    eventPublisher.publishEvent(new TicketAvanzadoEvent(ticket, agenteOrigen, pasoAnterior, ticketNvoEtapa.getNota()));

        ticketRepository.save(ticket);

        String idTicket = ticket.getIdTicket();

        Integer idPaso = ticket.getPasoActual().getIdPasosFlujo();

        return new Respuesta<>(true, "Ticket avanzado a la siguiente etapa", new TicketPasoResponse(idTicket, idPaso));

    }

    public Respuesta<TicketPasoResponse> cerrarTicket(TicketAvanzarEtapa ticketNvoEtapa) {
        Ticket ticket = ticketRepository.findById(ticketNvoEtapa.getIdTicket()).get();

        PasoFlujo pasoActual = ticket.getPasoActual();
        Agente agenteOrigen = ticket.getAgenteAsignado();

        EstadoTicket estadoCerrado = estadoTicketRepository.findByEstadoTicket("Cerrado");

        ticket.setEstado(estadoCerrado);
        ticket.setFechaActualizacion(LocalDateTime.now());

        ticketRepository.save(ticket);

        eventPublisher.publishEvent(new TicketAvanzadoEvent(ticket, agenteOrigen, pasoActual, ticketNvoEtapa.getNota()));

        String idTicket = ticket.getIdTicket();

        Integer idPaso = ticket.getPasoActual().getIdPasosFlujo();

        return new Respuesta<>(true, "Ticket cerrado correctamente", new TicketPasoResponse(idTicket, idPaso));
    }

}
