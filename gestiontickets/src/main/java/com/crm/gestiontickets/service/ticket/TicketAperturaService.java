package com.crm.gestiontickets.service.ticket;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.ticket.TicketApertura;
import com.crm.gestiontickets.dto.ticket.TicketCreacion;
import com.crm.gestiontickets.dto.ticket.TicketPasoResponse;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.Flujo;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.CategoriaRepository;
import com.crm.gestiontickets.repository.ClienteRepository;
import com.crm.gestiontickets.repository.EstadoTicketRepository;
import com.crm.gestiontickets.repository.FlujoRepository;
import com.crm.gestiontickets.repository.PasoFlujoRepository;
import com.crm.gestiontickets.repository.SecuencialTicketRepository;
import com.crm.gestiontickets.repository.TicketRepository;

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
