/* Patrón: estructural: Facade, centraliza los endpoints de ticket, delega los servicios
   comportamental: Mediator, coordina los servicios de TicketBusquedaService, TicketAperturaService, 
   TicketFlujoService y TicketAgenteService */

package com.crm.gestiontickets.ticket.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.agente.dto.IdAgente;
import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.IdTicket;
import com.crm.gestiontickets.ticket.dto.TicketApertura;
import com.crm.gestiontickets.ticket.dto.TicketAvanzarEtapa;
import com.crm.gestiontickets.ticket.dto.TicketCreacion;
import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaDetalle;
import com.crm.gestiontickets.ticket.dto.TicketPasoResponse;
import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketAsignadosEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketEstadoEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketsEnum;
import com.crm.gestiontickets.ticket.service.TicketAgenteService;
import com.crm.gestiontickets.ticket.service.TicketAperturaService;
import com.crm.gestiontickets.ticket.service.TicketBusquedaService;
import com.crm.gestiontickets.ticket.service.TicketFlujoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketBusquedaService ticketBusquedaService;

    @Autowired
    private TicketAperturaService ticketCreacionService;

    @Autowired
    private TicketFlujoService ticketFlujoService;

    @Autowired
    private TicketAgenteService ticketAgenteService;

    @PostMapping("/apertura")
    public Respuesta<TicketPasoResponse> aperturaTicket(@RequestBody TicketApertura ticketAperturaDTO) {
        return ticketCreacionService.aperturaTicket(ticketAperturaDTO);
    }

    @PutMapping("/crear-ticket")
    public Respuesta<TicketPasoResponse> creacionTicket(@RequestBody TicketCreacion ticketDetalleDTO) {
        return ticketCreacionService.crearTicket(ticketDetalleDTO);
    }

    @GetMapping("/obtener-ticket")
    public TicketDetalle obtenerTicket(@RequestParam String idTicket) {
        return ticketBusquedaService.obtenerTicketDTO(idTicket);
    }

    @GetMapping("/obtener-tickets-cliente")
    public Page<TicketDetalle> obtenerTicketsCliente(
            @RequestParam Long idCliente,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) FiltroTicketEstadoEnum estado,
            @RequestParam(required = false) FiltroFechaTicketEnum fechaOp,
            @RequestParam(required = false) LocalDate fecha) {
        return ticketBusquedaService.obtenerTicketsCliente(
                idCliente, page, pageSize, estado, fechaOp, fecha);
    }

    @GetMapping("/filtrar-ticket-etapa")
    public Respuesta<TicketEtapaDetalle> obtenerEstadoTicketEtapa(@RequestParam String idTicket,
            @RequestParam Integer idPaso) {
        return ticketBusquedaService.obtenerEstadoTicketEtapa(idTicket, idPaso);
    }

    @GetMapping("/otener-tickets-departamento")
    public Page<TicketDetalle> obtenerTicketsDepartamento(@RequestParam Integer idDepartamento, @RequestParam(defaultValue="0") int page, 
    @RequestParam(defaultValue="10") int pageSize, @RequestParam(required=false) FiltroTicketAsignadosEnum asignacion) {
        return ticketBusquedaService.obtenerTicketsDepartamento(idDepartamento, page, pageSize, asignacion);
    }

    @PutMapping("/avanzar-etapa")
    public Respuesta<TicketPasoResponse> avanzarEtapa(@RequestBody TicketAvanzarEtapa ticketNvoEtapa) {
        return ticketFlujoService.avanzarEtapa(ticketNvoEtapa);
    }

    @PutMapping("/cerrar-ticket")
    public Respuesta<TicketPasoResponse> cerrarTicket(@RequestBody TicketAvanzarEtapa ticketNvoEtapa) {
        return ticketFlujoService.cerrarTicket(ticketNvoEtapa);
    }

    @PatchMapping("{idTicket}/asignar-agente")
    public Respuesta<IdTicket> asignarAgenteATicket(@PathVariable String idTicket, @RequestBody IdAgente idAgente) {
        return ticketAgenteService.asignarAgenteATicket(idTicket, idAgente);
    }

    @GetMapping("/obtener-tickets-agente")
    public List<TicketEtapaAgenteDetalle> obtenerTicketsAgente(@RequestParam Integer idAgente,
            FiltroTicketsEnum filtroEstado) {
        return ticketBusquedaService.obtenerTicketsAgente(idAgente, filtroEstado);
    }
}
