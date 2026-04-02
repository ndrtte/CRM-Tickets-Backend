/*Patron: omportamiento: command, asigna un ticket a un agente */
package com.crm.gestiontickets.ticket.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.dto.IdAgente;
import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.agente.repository.AgenteRepository;
import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.IdTicket;
import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.entity.EstadoTicket;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.enums.FiltroTicketsAgenteEnum;
import com.crm.gestiontickets.ticket.mapper.TicketMapper;
import com.crm.gestiontickets.ticket.repository.HistoricoTicketRepository;
import com.crm.gestiontickets.ticket.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketAgenteService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private HistoricoTicketRepository historicoRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Transactional
    public Respuesta<IdTicket> asignarAgenteATicket(String idTicket, IdAgente idAgente) {

        Ticket ticket = ticketRepository.findById(idTicket).get();
        Agente agente = agenteRepository.findById(idAgente.getIdAgente()).get();

        ticket.setAgenteAsignado(agente);

        HistoricoTicket historico = historicoRepository.findTopByTicketOrderByFechaHistoricoDesc(ticket);
        if (historico != null) {
            historico.setAgenteDestino(agente);
        }

        ticketRepository.save(ticket);

        return new Respuesta<>(true, "Se asignó el ticket al agente con éxito", new IdTicket(idTicket));
    }
}
