/*Patron:  Comportamiento: Command,  encapsula la operacion de registro de historico de un ticket*/
package com.crm.gestiontickets.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.repository.HistoricoTicketRepository;

@Service
public class HistoricoTicketService {

    @Autowired
    private HistoricoTicketRepository historicoTicketRepository;

    public HistoricoTicket registrarHistorico(Ticket ticket, Agente agenteOrigen, Agente agenteDestino, PasoFlujo pasoOrigen,
            PasoFlujo pasoDestino) {
        HistoricoTicket historico = new HistoricoTicket();
        historico.setTicket(ticket);
        historico.setAgenteOrigen(agenteOrigen);
        historico.setAgenteDestino(agenteDestino);
        historico.setPasoOrigen(pasoOrigen);
        historico.setPasoDestino(pasoDestino);

        historicoTicketRepository.save(historico);

        return historico;
    }



}
