package com.crm.gestiontickets.agente.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.enums.TipoFechaEnum;
import com.crm.gestiontickets.ticket.repository.TicketRepository;
import com.crm.gestiontickets.ticket.mapper.TicketMapper;
import com.crm.gestiontickets.agente.enums.EstadoTicketAgenteEnum;

@Service
public class TicketBusquedaService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    public List<TicketDetalle> obtenerTicketsPorAgente(
            Integer idAgente,
            EstadoTicketAgenteEnum filtroEstado,
            TipoFechaEnum fechaOp,
            LocalDate fecha
    ) {

        return ticketRepository.findByAgenteAsignado(idAgente)
        .stream()
        .filter(t -> filtrarPorEstado(t, filtroEstado))
        .filter(t -> filtrarPorFecha(t.getFechaCreacion(), fechaOp, fecha))
        .map(ticketMapper::mapearTicketADetalle)
        .toList();
    }

    private boolean filtrarPorEstado(Ticket ticket, EstadoTicketAgenteEnum filtro) {

        if (filtro == null) {
            return true;
        }

        switch (filtro) {
            case EN_PROCESO:
                return ticket.getEstado().isActivo();
            case FINALIZADO:
                return !ticket.getEstado().isActivo();
            case NO_INICIADO:
                return ticket.getAgenteAsignado() == null;
            default:
                return true;
        }
    }

    private boolean filtrarPorFecha(LocalDateTime fechaCreacion, TipoFechaEnum op, LocalDate fecha) {

        if (fecha == null || op == null) {
            return true;
        }

        if (fechaCreacion == null) {
            return false;
        }

        LocalDate fechaTicket = fechaCreacion.toLocalDate();

        switch (op) {
            case MENOR:
                return fechaTicket.isBefore(fecha);
            case IGUAL:
                return fechaTicket.isEqual(fecha);
            case MAYOR:
                return fechaTicket.isAfter(fecha);
            default:
                return true;
        }
    }
}