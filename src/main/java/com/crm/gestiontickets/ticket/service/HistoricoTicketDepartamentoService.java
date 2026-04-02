package com.crm.gestiontickets.ticket.service;

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
public class HistoricoTicketDepartamentoService {

    @Autowired
    private HistoricoTicketRepository historicoTicketRepository;
    //Paginacion del historico de tickets por departamento, con filtros de estado, agente, fecha inicio y fecha fin
    public Page<TicketDetalle> filtrarHistoricoDepartamento(
        Integer idDepartamento,
        String estadoTicket,
        Integer idAgente,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Pageable pageable
    ) {
        Page<HistoricoTicket> historicos = historicoTicketRepository.filtrarHistorico(
            idDepartamento,
            estadoTicket,
            idAgente,
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