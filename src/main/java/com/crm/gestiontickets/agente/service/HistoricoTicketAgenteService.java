package com.crm.gestiontickets.agente.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.dto.builder.TicketDetalleBuilder;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.repository.HistoricoTicketRepository;

@Service
public class HistoricoTicketAgenteService {

    @Autowired
    private HistoricoTicketRepository historicoTicketRepository;

    public Page<TicketDetalle> filtrarHistoricoAgente(
        Integer idAgente,
        String estadoTicket,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Pageable pageable
    ) {

        Page<HistoricoTicket> historicos = historicoTicketRepository.filtrarPorAgente(
            idAgente,
            estadoTicket,
            fechaInicio,
            fechaFin,
            pageable
        );

        return historicos.map(h -> new TicketDetalleBuilder()
                .conTicket(h.getTicket())
                .conCliente(h.getTicket().getCliente())
                .conCategoria(h.getTicket().getCategoria())
                .conAgente(h.getAgenteDestino())
                .conDepartamento(h.getPasoDestino())
                .conEstado(h.getTicket().getEstado())
                .build()
        );
    }
}