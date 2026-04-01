package com.crm.gestiontickets.ticket.service.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.ticket.dto.event.HistoricoCreadoEvent;
import com.crm.gestiontickets.ticket.dto.event.TicketAvanzadoEvent;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.service.HistoricoTicketService;

@Component
public class HistoricoObserver {

    @Autowired
    private HistoricoTicketService historicoTicketService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @EventListener
    public void alAvanzarEtapa(TicketAvanzadoEvent evento) {
        HistoricoTicket historico = historicoTicketService.registrarHistorico(
                evento.getTicket(),
                evento.getAgenteOrigen(),
                evento.getTicket().getAgenteAsignado(),
                evento.getPasoAnterior(),
                evento.getTicket().getPasoActual()
        );

        eventPublisher.publishEvent(new HistoricoCreadoEvent(historico, evento.getNota()));
    }
}