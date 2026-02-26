package com.crm.gestiontickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.TicketAperturaDTO;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.EstadoTicketRepository;
import com.crm.gestiontickets.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EstadoTicketRepository estadoTicketRepository;

    @Autowired
    private AgenteRepository agenteRepository;
    
    public String aperturaTicket(TicketAperturaDTO ticketAperturaDTO){

        Ticket ticketArpetura = new Ticket();
        Agente agente = agenteRepository.findById(ticketAperturaDTO.getIdAgenteAsignado()).get();
        EstadoTicket estadosTicket = estadoTicketRepository.findByEstadoTicket("Nuevo");
        
        ticketArpetura.setIdTicket("");
        ticketArpetura.setAgenteAsignado(agente);
        ticketArpetura.setEstado(estadosTicket);
        ticketArpetura.setActivo('S');

        ticketRepository.save(ticketArpetura);
        return "";
    }

}
