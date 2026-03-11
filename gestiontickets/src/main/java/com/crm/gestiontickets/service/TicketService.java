package com.crm.gestiontickets.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.IdTicket;
import com.crm.gestiontickets.dto.TicketApertura;
import com.crm.gestiontickets.dto.TicketCreacion;
import com.crm.gestiontickets.dto.TicketDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.Flujo;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.mapper.TicketMapper;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.CategoriaRepository;
import com.crm.gestiontickets.repository.ClienteRepository;
import com.crm.gestiontickets.repository.EstadoTicketRepository;
import com.crm.gestiontickets.repository.FlujoRepository;
import com.crm.gestiontickets.repository.HistoricoTicketRepository;
import com.crm.gestiontickets.repository.PasoFlujoRepository;
import com.crm.gestiontickets.repository.SecuencialTicketRepository;
import com.crm.gestiontickets.repository.TicketRepository;

@Service
public class TicketService {

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
    private TicketMapper ticketMapper;

    @Autowired
    private HistorialTicketService historialTicketService;

    @Autowired
    private HistoricoTicketRepository historicoTicketRepository;


    public IdTicket aperturaTicket(TicketApertura ticketAperturaDTO) {

        Ticket ticketArpetura = new Ticket();

        String idTicket = secuencialTicketRepository.generarIdTicket();

        Agente agente = agenteRepository.findById(ticketAperturaDTO.getIdAgenteAsignado()).get();
        EstadoTicket estadosTicket = estadoTicketRepository.findByEstadoTicket("Nuevo");
        LocalDateTime fechaAsignacion = LocalDateTime.now();
        Cliente cliente = clienteRepository.findById(ticketAperturaDTO.getIdCliente()).get();

        ticketArpetura.setIdTicket(idTicket);
        ticketArpetura.setAgenteAsignado(agente);
        ticketArpetura.setCliente(cliente);
        ticketArpetura.setEstado(estadosTicket);
        ticketArpetura.setActivo('S');
        ticketArpetura.setFechaAsignacion(fechaAsignacion);

        ticketRepository.save(ticketArpetura);

        IdTicket idTicketDTO = new IdTicket(idTicket);

        return idTicketDTO;
    }

    public IdTicket crearTicket(TicketCreacion nvoTicket) {

        Ticket ticket = ticketRepository.findById(nvoTicket.getIdTicket()).get();

        Agente agenteDestino = ticket.getAgenteAsignado();

        Categoria categoria = categoriaRepository.findById(nvoTicket.getIdCategoria()).get();
        ticket.setCategoria(categoria);

        Flujo flujo = flujoRepository.findByCategoria(categoria);

        PasoFlujo primerPaso = getPrimerPasoPendiente(flujo, ticket);
        ticket.setPasoActual(primerPaso);

        ticket.setEstado(estadoTicketRepository.findByEstadoTicket("En Proceso"));

        ticket.setFechaActualizacion(LocalDateTime.now());

        historialTicketService.registrarHistorico(ticket, null, agenteDestino, null, primerPaso);

        ticketRepository.save(ticket);

        return new IdTicket(ticket.getIdTicket());
    }

    private PasoFlujo getPrimerPasoPendiente(Flujo flujo, Ticket ticket) {
        List<PasoFlujo> pasos = pasoFlujoRepository.findByIdFlujoOrderByOrdenAsc(flujo);
        for (PasoFlujo paso : pasos) {
            if (!pasoCompletado(ticket, paso)) {
                return paso;
            }
        }
        return pasos.get(0);
    }

    private boolean pasoCompletado(Ticket ticket, PasoFlujo paso) {
        return historicoTicketRepository.existsByTicketAndPasoDestino(ticket, paso);
    }


    public TicketDetalle obtenerTicketDTO(String idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).get();
        return ticketMapper.mapearTicketADetalle(ticket);
    }

    public List<TicketDetalle> obtenerTicketsCliente(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).get();

        List<Ticket> listaTickets = ticketRepository.findByCliente(cliente);
        List<TicketDetalle> listaTicketsDTO = new ArrayList<>();

        for (Ticket ticket : listaTickets) {
            TicketDetalle detalle = ticketMapper.mapearTicketADetalle(ticket);
            listaTicketsDTO.add(detalle);
        }

        return listaTicketsDTO;
    }

    public List<TicketDetalle> obtenerTicketsDepartamento(Integer idDepartamento) {

        List<TicketDetalle> listaTicketsDTO = new ArrayList<>();

        List<Ticket> listaTicket = ticketRepository.findTicketsByDepartamento(idDepartamento);

        for (Ticket ticket : listaTicket) {
            TicketDetalle ticketDetalle = ticketMapper.mapearTicketADetalle(ticket);
            listaTicketsDTO.add(ticketDetalle);
        }

        return listaTicketsDTO;
    }

}
