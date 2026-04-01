/*Patron: Creacional: Builder,  construccion de un ticket completo */
package com.crm.gestiontickets.ticket.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.agente.repository.AgenteRepository;
import com.crm.gestiontickets.cliente.entity.Cliente;
import com.crm.gestiontickets.cliente.repository.ClienteRepository;
import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.TicketApertura;
import com.crm.gestiontickets.ticket.dto.TicketCreacion;
import com.crm.gestiontickets.ticket.dto.TicketPasoResponse;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.EstadoTicket;
import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.repository.CategoriaRepository;
import com.crm.gestiontickets.ticket.repository.EstadoTicketRepository;
import com.crm.gestiontickets.ticket.repository.FlujoRepository;
import com.crm.gestiontickets.ticket.repository.PasoFlujoRepository;
import com.crm.gestiontickets.ticket.repository.SecuencialTicketRepository;
import com.crm.gestiontickets.ticket.repository.TicketRepository;

@Service
public class TicketAperturaService {

    
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EstadoTicketRepository estadoTicketRepository;

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private SecuencialTicketRepository secuencialTicketRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PasoFlujoRepository pasoFlujoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FlujoRepository flujoRepository;

    @Autowired
    private HistoricoTicketService historicoTicketService;

    @Autowired
    private NotaService notaService;

    public Respuesta<TicketPasoResponse> aperturaTicket(TicketApertura dto) {

        String idTicket = secuencialTicketRepository.generarIdTicket();

        Agente agente = agenteRepository.findById(dto.getIdAgenteAsignado()).get();
        Cliente cliente = clienteRepository.findById(dto.getIdCliente()).get();

        EstadoTicket estadoNuevo = estadoTicketRepository.findByEstadoTicket("Nuevo");
        PasoFlujo pasoApertura = pasoFlujoRepository.findByDescripcion("APERTURA");

        Ticket ticket = crearTicketBase(idTicket, cliente, agente, estadoNuevo, pasoApertura);

        ticketRepository.save(ticket);

        return new Respuesta<>(true, "Ticket abierto correctamente", new TicketPasoResponse(idTicket, pasoApertura.getIdPasosFlujo()));
    }

    public Respuesta<TicketPasoResponse> crearTicket(TicketCreacion dto) {
        Ticket ticket = ticketRepository.findById(dto.getIdTicket()).get();

        PasoFlujo pasoAnterior = ticket.getPasoActual();
        Agente agenteOrigen = ticket.getAgenteAsignado();

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria()).get();
        Flujo flujo = flujoRepository.findByCategoria(categoria);

        PasoFlujo primerPaso = pasoFlujoRepository
                .findFirstByIdFlujoOrderByOrdenAsc(flujo);

        EstadoTicket estadoProceso = estadoTicketRepository
                .findByEstadoTicket("En Proceso");


        ticket.setCategoria(categoria);
        ticket.setPasoActual(primerPaso);
        ticket.setEstado(estadoProceso);
        ticket.setFechaActualizacion(LocalDateTime.now());
        ticket.setAgenteAsignado(null);

        HistoricoTicket historico = historicoTicketService.registrarHistorico(
                ticket,
                agenteOrigen,
                ticket.getAgenteAsignado(),
                pasoAnterior,
                primerPaso
        );

        notaService.registrarNota(dto.getNota(), historico);

        ticketRepository.save(ticket);

        String idTicket = ticket.getIdTicket();
        Integer idPaso = ticket.getPasoActual().getIdPasosFlujo();


        return new Respuesta<>(true, "Ticket creado correctamente", new TicketPasoResponse(idTicket, idPaso));
    }

        // Metodos auxiliares
    private Ticket crearTicketBase(String idTicket, Cliente cliente, Agente agente, EstadoTicket estado, PasoFlujo paso) {
        Ticket ticket = new Ticket();
        ticket.setIdTicket(idTicket);
        ticket.setCliente(cliente);
        ticket.setAgenteAsignado(agente);
        ticket.setEstado(estado);
        ticket.setPasoActual(paso);
        ticket.setActivo('S');
        ticket.setFechaAsignacion(LocalDateTime.now());
        return ticket;
    }

}