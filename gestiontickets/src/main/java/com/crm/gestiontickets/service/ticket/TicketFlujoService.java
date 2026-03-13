package com.crm.gestiontickets.service.ticket;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.ticket.IdTicket;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.Flujo;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.repository.EstadoTicketRepository;
import com.crm.gestiontickets.repository.PasoFlujoRepository;
import com.crm.gestiontickets.repository.TicketRepository;

@Service
public class TicketFlujoService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PasoFlujoRepository pasoFlujoRepository;

    @Autowired
    private HistorialTicketService historialTicketService;

    @Autowired
    private NotaService notaService;

    @Autowired
    private EstadoTicketRepository estadoTicketRepository;

    public Respuesta<IdTicket> avanzarEtapa(String idTicket, String nota) {
        Ticket ticket = ticketRepository.findById(idTicket).get();

        PasoFlujo pasoActual = ticket.getPasoActual();
        PasoFlujo pasoAnterior = pasoActual;

        Flujo flujo = pasoActual.getIdFlujo();

        Integer siguienteOrden = pasoActual.getOrden() + 1;

        PasoFlujo siguientePaso = pasoFlujoRepository
                .findByIdFlujoAndOrden(flujo, siguienteOrden);

        Agente agenteOrigen = ticket.getAgenteAsignado();

        ticket.setPasoActual(siguientePaso);
        ticket.setFechaActualizacion(LocalDateTime.now());

        HistoricoTicket historico = historialTicketService.registrarHistorico(
                ticket,
                agenteOrigen,
                null,
                pasoAnterior,
                siguientePaso
        );

        notaService.registrarNota(nota, historico);

        ticketRepository.save(ticket);

        return new Respuesta<>(true, "Ticket avanzado a la siguiente etapa", new IdTicket(ticket.getIdTicket()));
    }

    public Respuesta<IdTicket> cerrarTicket(String idTicket, String nota) {
        Ticket ticket = ticketRepository.findById(idTicket).get();

        PasoFlujo pasoActual = ticket.getPasoActual();
        Agente agenteOrigen = ticket.getAgenteAsignado();

        EstadoTicket estadoCerrado = estadoTicketRepository.findByEstadoTicket("Cerrado");

        ticket.setEstado(estadoCerrado);
        ticket.setFechaActualizacion(LocalDateTime.now());

        HistoricoTicket historico = historialTicketService.registrarHistorico(
                ticket,
                agenteOrigen,
                null,
                pasoActual,
                pasoActual
        );

        notaService.registrarNota(nota, historico);

        ticketRepository.save(ticket);

        return new Respuesta<>(true, "Ticket cerrado correctamente", new IdTicket(ticket.getIdTicket()));
    }

}
