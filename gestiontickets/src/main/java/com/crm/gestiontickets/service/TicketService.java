package com.crm.gestiontickets.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.IdTicketDTO;
import com.crm.gestiontickets.dto.TicketAperturaDTO;
import com.crm.gestiontickets.dto.TicketCreacionDTO;
import com.crm.gestiontickets.dto.TicketDetalleDTO;
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
    private HistoricoTicketRepository historicoTicketRepository;
    
    public IdTicketDTO aperturaTicket(TicketAperturaDTO ticketAperturaDTO){

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

        IdTicketDTO idTicketDTO = new IdTicketDTO(idTicket);
        
        return idTicketDTO;
    }

    public IdTicketDTO crearTicket(TicketCreacionDTO ticketDetalleDTO) {

        String idTicket = ticketDetalleDTO.getIdTicket();
        Ticket ticket = ticketRepository.findById(idTicket).get();

        Agente agenteOrigen = ticket.getAgenteAsignado();
        Agente agenteDestino = agenteRepository.findById(ticketDetalleDTO.getIdAgente()).get();

        Categoria categoria = categoriaRepository.findById(ticketDetalleDTO.getIdCategoria()).get();
        Flujo flujo = flujoRepository.findByCategoria(categoria);
        PasoFlujo pasoActual = pasoFlujoRepository.findByIdFlujoAndOrden(flujo, 1);

        ticket.setAgenteAsignado(agenteDestino);
        ticket.setCategoria(categoria);
        ticket.setPasoActual(pasoActual);
        ticket.setEstado(estadoTicketRepository.findByEstadoTicket("En Proceso"));
        ticket.setFechaActualizacion(LocalDateTime.now());

        ticketRepository.save(ticket);

        registrarHistorico(ticket, agenteOrigen, agenteDestino, null, pasoActual);

        return new IdTicketDTO(ticket.getIdTicket());
    }

    private void registrarHistorico(Ticket ticket, Agente agenteOrigen, Agente agenteDestino, PasoFlujo pasoOrigen, PasoFlujo pasoDestino) {
        HistoricoTicket historico = new HistoricoTicket();
        historico.setTicket(ticket);
        historico.setAgenteOrigen(agenteOrigen);
        historico.setAgenteDestino(agenteDestino);
        historico.setPasoOrigen(pasoOrigen);
        historico.setPasoDestino(pasoDestino);

        historicoTicketRepository.save(historico);
    }

    public TicketDetalleDTO obtenerTicketDTO (String idTicket){
        Ticket ticket = ticketRepository.findById(idTicket).get();

        Cliente cliente = ticket.getCliente();
        Categoria categoria = ticket.getCategoria();
        PasoFlujo pasoActual = ticket.getPasoActual();
        Agente agente  = ticket.getAgenteAsignado();
        EstadoTicket estado = ticket.getEstado();
        
        TicketDetalleDTO ticketDetalle = new TicketDetalleDTO();

        ticketDetalle.setIdTicket(idTicket);
        ticketDetalle.setIdCliente(cliente.getIdCliente());
        ticketDetalle.setNombreCliente(cliente.getNombre()+" "+cliente.getApellido());
        ticketDetalle.setIdCategoria(categoria.getIdCategoria());
        ticketDetalle.setCategoria(categoria.getNombre());
        ticketDetalle.setIdPasoActual(pasoActual.getIdPasosFlujo());
        ticketDetalle.setPasoActual(pasoActual.getDescripcion());
        ticketDetalle.setIdAgente(agente.getIdAgente());
        ticketDetalle.setNombreAgente(agente.getNombre()+" "+agente.getApellido());
        ticketDetalle.setEstado(estado.getEstadoTicket());
        ticketDetalle.setFechaCreacion(ticket.getFechaCreacion());


        return ticketDetalle;
    }

}