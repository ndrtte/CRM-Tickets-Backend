package com.crm.gestiontickets.ticket.service.listener;

import org.springframework.stereotype.Component;

import com.crm.gestiontickets.ticket.dto.evento.TicketCreadoEvent;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.interfaces.ITicketCreadoObserver;
import com.crm.gestiontickets.ticket.service.HistoricoTicketService;
import com.crm.gestiontickets.ticket.service.NotaService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor

public class NotaHistoricoListener implements ITicketCreadoObserver {

    private final HistoricoTicketService historicoTicketService;
    private final NotaService notaService;

    @Override
    public void enTicketCreado(TicketCreadoEvent event) {
        HistoricoTicket historico = historicoTicketService.registrarHistorico(
            event.getTicket(),
            event.getAgenteOrigen(),
            event.getTicket().getAgenteAsignado(),
            event.getPasoAnterior(),
            event.getPasoNuevo()
        );

        notaService.registrarNota(event.getDto().getNota(), historico);
    }
}
