package com.crm.gestiontickets.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.TicketAperturaDTO;
import com.crm.gestiontickets.dto.TicketDetalleDTO;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.CategoriaRepository;
import com.crm.gestiontickets.repository.ClienteRepository;
import com.crm.gestiontickets.repository.EstadoTicketRepository;
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
    
    public String aperturaTicket(TicketAperturaDTO ticketAperturaDTO){

        Ticket ticketArpetura = new Ticket();

        String idTicket = secuencialTicketRepository.generarIdTicket();

        Agente agente = agenteRepository.findById(ticketAperturaDTO.getIdAgenteAsignado()).get();
        EstadoTicket estadosTicket = estadoTicketRepository.findByEstadoTicket("Nuevo");
        LocalDateTime fechaActualizacion = LocalDateTime.now();


        ticketArpetura.setIdTicket(idTicket);
        ticketArpetura.setAgenteAsignado(agente);
        ticketArpetura.setEstado(estadosTicket);
        ticketArpetura.setActivo('S');
        ticketArpetura.setFechaActualizacion(fechaActualizacion);
        ticketArpetura.setFechaAsignacion(fechaActualizacion);

        ticketRepository.save(ticketArpetura);
        
        return ticketArpetura.getIdTicket();
    }

    public String crearTicket(TicketDetalleDTO ticketDetalleDTO){
        Ticket ticket = ticketRepository.findById(ticketDetalleDTO.getIdTicket()).get();

        Cliente cliente = clienteRepository.findById(ticketDetalleDTO.getIdCliente()).get();

        Categoria categoria = categoriaRepository.findById(ticketDetalleDTO.getIdCategoria()).get();

        PasoFlujo pasoActual = pasoFlujoRepository.findById(ticketDetalleDTO.getIdPasoActual()).get();

        Agente agenteAsignado = agenteRepository.findById(ticketDetalleDTO.getIdAgenteAsignado()).get();

        EstadoTicket estado = estadoTicketRepository.findByEstadoTicket("En Proceso");

        ticket.setCliente(cliente);
        ticket.setCategoria(categoria);
        ticket.setPasoActual(pasoActual);
        ticket.setAgenteAsignado(agenteAsignado);
        ticket.setEstado(estado);
        ticket.setActivo('S');
    
        ticketRepository.save(ticket);

        return "Funciono, no soy una inutil";
    }

}
