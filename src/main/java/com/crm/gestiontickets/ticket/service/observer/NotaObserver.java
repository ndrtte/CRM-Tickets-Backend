package com.crm.gestiontickets.ticket.service.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.ticket.dto.event.HistoricoCreadoEvent;
import com.crm.gestiontickets.ticket.service.NotaService;

@Component
public class NotaObserver {
    @Autowired private NotaService notaService;

    @EventListener
    public void alCrearHistorico(HistoricoCreadoEvent evento) {
        notaService.registrarNota(evento.getNota(), evento.getHistorico());
    }
}
