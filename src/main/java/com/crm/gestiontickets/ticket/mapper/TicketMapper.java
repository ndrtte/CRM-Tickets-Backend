/*Patron: comportamental: Mapper, convierte entidades en DTOs */
package com.crm.gestiontickets.ticket.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.ticket.dto.builder.TicketDetalleBuilder;
import com.crm.gestiontickets.ticket.dto.builder.TicketEtapaAgenteDetalleBuilder;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;
import com.crm.gestiontickets.ticket.repository.HistoricoTicketRepository;
import com.crm.gestiontickets.ticket.repository.TicketRepository;

@Component
public class TicketMapper {

    @Autowired
    private PasoFlujoMapper pasoFlujoMapper;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private HistoricoTicketRepository historicoRepository;

    public TicketDetalle mapearTicketADetalle(Ticket ticket) {
        return new TicketDetalleBuilder()
                .conTicket(ticket)
                .conCliente(ticket.getCliente())
                .conCategoria(ticket.getCategoria())
                .conAgente(ticket.getAgenteAsignado())
                .conDepartamento(ticket.getPasoActual())
                .conEstado(ticket.getEstado())
                .conListaEtapas(pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), ticket.getPasoActual()))
                .build();
    }

    public Page<TicketEtapaAgenteDetalle> mapearTicketsEnProceso(Agente agente, Pageable pageable,
            FiltroFechaTicketEnum fechaOp, LocalDate fecha) {
        Page<Ticket> tickets = ticketRepository.findTicketsEnProceso(agente, fechaOp, fecha, pageable);

        return tickets.map(ticket -> {
            PasoFlujo paso = ticket.getPasoActual();
            String estadoEtapa = (ticket.getEstado() != null
                    && "Cerrado".equalsIgnoreCase(ticket.getEstado().getEstadoTicket()))
                            ? "Finalizado"
                            : "En Proceso";

            return new TicketEtapaAgenteDetalleBuilder()
                    .conIdTicket(ticket.getIdTicket())
                    .conCliente(ticket.getCliente())
                    .conCategoria(ticket.getCategoria())
                    .conAgente(ticket.getAgenteAsignado())
                    .conDepartamento(paso)
                    .conEstadoEtapa(estadoEtapa)
                    .conFechaCreacion(ticket.getFechaCreacion())
                    .conListaEtapas(pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), paso))
                    .conEstado(ticket.getEstado())
                    .build();
        });
    }

    public Page<TicketEtapaAgenteDetalle> mapearTicketsFinalizados(Agente agente, Pageable pageable,
            FiltroFechaTicketEnum fechaOp, LocalDate fecha) {

        Page<HistoricoTicket> historicos = historicoRepository.findHistoricoTicketByAgenteOrigen(agente, fechaOp, fecha,
                pageable);

        return historicos.map(historico -> {
            Ticket ticket = historico.getTicket();
            PasoFlujo pasoDestino = historico.getPasoDestino();

            return new TicketEtapaAgenteDetalleBuilder()
                    .conIdTicket(ticket.getIdTicket())
                    .conCliente(ticket.getCliente())
                    .conCategoria(ticket.getCategoria())
                    .conAgente(historico.getAgenteOrigen())
                    .conDepartamento(pasoDestino)
                    .conEstadoEtapa("Finalizado")
                    .conFechaCreacion(ticket.getFechaCreacion())
                    .conEstado(ticket.getEstado())
                    .conListaEtapas(pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), ticket.getPasoActual()))
                    .build();
        });
    }

    public Page<TicketEtapaAgenteDetalle> mapearTicketsTodos(
            Agente agente, Pageable pageable, FiltroFechaTicketEnum fechaOp, LocalDate fecha) {

        List<TicketEtapaAgenteDetalle> enProceso = mapearTicketsEnProceso(agente, Pageable.unpaged(), fechaOp, fecha)
                .getContent();
        List<TicketEtapaAgenteDetalle> finalizados = mapearTicketsFinalizados(agente, Pageable.unpaged(), fechaOp,
                fecha).getContent();

        Map<String, TicketEtapaAgenteDetalle> ticketsMap = new LinkedHashMap<>();
        for (TicketEtapaAgenteDetalle t : enProceso) {
            ticketsMap.put(t.getIdTicket(), t);
        }
        for (TicketEtapaAgenteDetalle t : finalizados) {
            ticketsMap.putIfAbsent(t.getIdTicket(), t);
        }

        List<TicketEtapaAgenteDetalle> todos = new ArrayList<>(ticketsMap.values());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), todos.size());
        List<TicketEtapaAgenteDetalle> sublist = start > end ? Collections.emptyList() : todos.subList(start, end);

        return new PageImpl<>(sublist, pageable, todos.size());
    }

}
