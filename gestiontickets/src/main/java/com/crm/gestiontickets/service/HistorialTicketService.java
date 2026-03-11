package com.crm.gestiontickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.repository.HistoricoTicketRepository;

@Service
public class HistorialTicketService {

    @Autowired
    private HistoricoTicketRepository historicoTicketRepository;

    public void registrarHistorico(Ticket ticket, Agente agenteOrigen, Agente agenteDestino, PasoFlujo pasoOrigen,
            PasoFlujo pasoDestino) {
        HistoricoTicket historico = new HistoricoTicket();
        historico.setTicket(ticket);
        historico.setAgenteOrigen(agenteOrigen);
        historico.setAgenteDestino(agenteDestino);
        historico.setPasoOrigen(pasoOrigen);
        historico.setPasoDestino(pasoDestino);

        historicoTicketRepository.save(historico);
    }

}
