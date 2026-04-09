package com.crm.gestiontickets.ticket.interfaces;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaDetalle;
import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketAsignadosEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketEstadoEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketsAgentesEnum;

//Interface Segregation
public interface ITicketBusquedaService {

    TicketDetalle obtenerTicketDTO(String idTicket);

    Page<TicketDetalle> obtenerTicketsCliente(
            Long idCliente,
            int page,
            int pageSize,
            FiltroTicketEstadoEnum estado,
            FiltroFechaTicketEnum fechaOp,
            LocalDate fecha
    );

    Page<TicketDetalle> obtenerTicketsDepartamento(
            Integer idDepartamento,
            int page,
            int pageSize,
            FiltroTicketAsignadosEnum asignacion
    );

    Page<TicketEtapaAgenteDetalle> obtenerTicketsAgente(
            Integer idAgente,
            int page,
            int pageSize,
            FiltroTicketsAgentesEnum filtro,
            FiltroFechaTicketEnum fechaOp,
            LocalDate fecha
    );

    Respuesta<TicketEtapaDetalle> obtenerEstadoTicketEtapa(
            String idTicket,
            Integer idPaso
    );
}
