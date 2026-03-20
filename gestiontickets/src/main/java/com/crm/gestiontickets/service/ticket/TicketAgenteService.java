package com.crm.gestiontickets.service.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.agente.IdAgente;
import com.crm.gestiontickets.dto.ticket.IdTicket;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.HistoricoTicketRepository;
import com.crm.gestiontickets.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketAgenteService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private HistoricoTicketRepository historicoRepository;

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
