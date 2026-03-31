/*Patron: comportamental: Mapper, convierte entidades en DTOs */
package com.crm.gestiontickets.ticket.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.ticket.dto.builder.TicketDetalleBuilder;
import com.crm.gestiontickets.ticket.dto.builder.TicketEtapaAgenteDetalleBuilder;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
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

    public List<TicketEtapaAgenteDetalle> mapearEnProceso(Agente agente) {
        List<Ticket> tickets = ticketRepository.findByAgenteAsignado(agente);

        return tickets.stream()
                .map(ticket -> {
                    PasoFlujo paso = ticket.getPasoActual();
                    String estadoEtapa = (ticket.getEstado() != null && "Cerrado".equalsIgnoreCase(ticket.getEstado().getEstadoTicket()))
                            ? "Finalizado" : "En proceso";

                    return new TicketEtapaAgenteDetalleBuilder()
                            .conIdTicket(ticket.getIdTicket())
                            .conCliente(ticket.getCliente())
                            .conCategoria(ticket.getCategoria())
                            .conAgente(ticket.getAgenteAsignado())
                            .conDepartamento(paso)
                            .conEstadoEtapa(estadoEtapa)
                            .conFechaCreacion(ticket.getFechaCreacion())
                            .conListaEtapas(pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), paso))
                            .build();
                })
                .toList();
    }

    public List<TicketEtapaAgenteDetalle> mapearTodos(Agente agente) {
        List<Ticket> enProceso = ticketRepository.findByAgenteAsignado(agente);
        List<HistoricoTicket> historicos = historicoRepository.findHistoricoTicketByAgenteOrigen(agente);

        List<TicketEtapaAgenteDetalle> responseEnProceso = enProceso.stream()
                .map(ticket -> {
                    PasoFlujo paso = ticket.getPasoActual();
                    String estadoEtapa = (ticket.getEstado() != null && "Cerrado".equalsIgnoreCase(ticket.getEstado().getEstadoTicket()))
                            ? "Finalizado" : "En proceso";

                    return new TicketEtapaAgenteDetalleBuilder()
                            .conIdTicket(ticket.getIdTicket())
                            .conCliente(ticket.getCliente())
                            .conCategoria(ticket.getCategoria())
                            .conAgente(ticket.getAgenteAsignado())
                            .conDepartamento(paso)
                            .conEstadoEtapa(estadoEtapa)
                            .conFechaCreacion(ticket.getFechaCreacion())
                            .conListaEtapas(pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), paso))
                            .build();
                })
                .toList();

        Set<String> idsEnProceso = enProceso.stream()
                .map(Ticket::getIdTicket)
                .collect(Collectors.toSet());

        List<TicketEtapaAgenteDetalle> responseFinalizados = historicos.stream()
                .filter(h -> !idsEnProceso.contains(h.getTicket().getIdTicket()))
                .map(historico -> {
                    Ticket ticket = historico.getTicket();
                    PasoFlujo paso = historico.getPasoDestino();
                    String estadoEtapa = "Finalizado";

                    return new TicketEtapaAgenteDetalleBuilder()
                            .conIdTicket(ticket.getIdTicket())
                            .conCliente(ticket.getCliente())
                            .conCategoria(ticket.getCategoria())
                            .conAgente(historico.getAgenteOrigen())
                            .conDepartamento(paso)
                            .conEstadoEtapa(estadoEtapa)
                            .conFechaCreacion(ticket.getFechaCreacion())
                            .conListaEtapas(pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), ticket.getPasoActual()))
                            .build();
                })
                .toList();

        List<TicketEtapaAgenteDetalle> resultado = new ArrayList<>();
        resultado.addAll(responseEnProceso);
        resultado.addAll(responseFinalizados);

        return resultado;
    }

    public  List<TicketEtapaAgenteDetalle> mapearFinalizados(Agente agente) {
        List<HistoricoTicket> historicos = historicoRepository.findHistoricoTicketByAgenteOrigen(agente);

        return historicos.stream()
                .map(historico -> {
                    Ticket ticket = historico.getTicket();
                    PasoFlujo paso = historico.getPasoDestino();
                    String estadoEtapa = "Finalizado";

                    return new TicketEtapaAgenteDetalleBuilder()
                            .conIdTicket(ticket.getIdTicket())
                            .conCliente(ticket.getCliente())
                            .conCategoria(ticket.getCategoria())
                            .conAgente(historico.getAgenteOrigen())
                            .conDepartamento(paso)
                            .conEstadoEtapa(estadoEtapa)
                            .conFechaCreacion(ticket.getFechaCreacion())
                            .conListaEtapas(pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), ticket.getPasoActual()))
                            .build();
                })
                .toList();
    }

}
