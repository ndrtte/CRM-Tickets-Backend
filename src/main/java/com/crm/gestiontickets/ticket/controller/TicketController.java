/* Patrón: estructural: Facade, centraliza los endpoints de ticket, delega los servicios
   comportamental: Mediator, coordina los servicios de TicketBusquedaService, TicketAperturaService, 
   TicketFlujoService y TicketAgenteService */

package com.crm.gestiontickets.ticket.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.crm.gestiontickets.agente.service.HistoricoTicketAgenteService;
import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.IdTicket;
import com.crm.gestiontickets.ticket.dto.TicketApertura;
import com.crm.gestiontickets.ticket.dto.TicketAvanzarEtapa;
import com.crm.gestiontickets.ticket.dto.TicketCreacion;
import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaDetalle;
import com.crm.gestiontickets.ticket.dto.TicketPasoResponse;
import com.crm.gestiontickets.ticket.entity.EstadoTicket;
import com.crm.gestiontickets.ticket.enums.FiltroTicketsAgenteEnum;
import com.crm.gestiontickets.ticket.enums.TipoFechaEnum;
import com.crm.gestiontickets.ticket.service.HistoricoTicketDepartamentoService;
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

    //paginacion
    @Autowired
    private HistoricoTicketDepartamentoService historicoTicketDepartamentoService;

    @Autowired
private HistoricoTicketAgenteService historicoTicketAgenteService;

    @PostMapping("/apertura")
    public Respuesta<TicketPasoResponse> aperturaTicket(@RequestBody TicketApertura ticketAperturaDTO){
        return ticketCreacionService.aperturaTicket(ticketAperturaDTO);
    }

    @PutMapping("/crear-ticket")
    public Respuesta<TicketPasoResponse> creacionTicket(@RequestBody TicketCreacion ticketDetalleDTO){
        return ticketCreacionService.crearTicket(ticketDetalleDTO);
    }

    @GetMapping("/obtener-ticket")
    public TicketDetalle obtenerTicket(@RequestParam String idTicket) {
        return ticketBusquedaService.obtenerTicketDTO(idTicket);
    }
    
    @GetMapping("/obtener-tickets-cliente")
    public List<TicketDetalle> obtenerTicketsCliente(@RequestParam Long idCliente){
        return ticketBusquedaService.obtenerTicketsCliente(idCliente);
    }

    @GetMapping("/filtrar-ticket-etapa")
    public Respuesta<TicketEtapaDetalle> obtenerEstadoTicketEtapa(@RequestParam String idTicket, @RequestParam Integer idPaso) {
        return ticketBusquedaService.obtenerEstadoTicketEtapa(idTicket, idPaso);
    }
    
    @GetMapping("/otener-tickets-departamento")
    public List<TicketDetalle> obtenerTicketsDepartamento(@RequestParam Integer idDepartamento) {
        return ticketBusquedaService.obtenerTicketsDepartamento(idDepartamento);
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
    public Respuesta<IdTicket> asignarAgenteATicket(@PathVariable String idTicket, @RequestBody IdAgente idAgente){
        return ticketAgenteService.asignarAgenteATicket(idTicket, idAgente);
    }

    @GetMapping("/obtener-tickets-agente")
    public List<TicketEtapaAgenteDetalle> obtenerTicketsAgente(@RequestParam Integer idAgente, FiltroTicketsAgenteEnum filtroEstado) {
        return ticketBusquedaService.obtenerTicketsAgente(idAgente, filtroEstado);
    }

    //paginacion
    @GetMapping("/historico-departamento")
    public Page<TicketDetalle> obtenerHistoricoDepartamento(
        @RequestParam Integer idDepartamento,
        @RequestParam(required = false) String estadoTicket,
        @RequestParam(required = false) Integer idAgente,
        @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
        @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
) {
    return historicoTicketDepartamentoService.filtrarHistoricoDepartamento(
            idDepartamento,
            estadoTicket,
            idAgente,
            fechaInicio,
            fechaFin,
            PageRequest.of(page, size)
    );
}

@GetMapping("/historico-agente")
public Page<TicketDetalle> obtenerHistoricoAgente(
    @RequestParam Integer idAgente,
    @RequestParam(required = false) String estadoTicket,
    @RequestParam(required = false)
    @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime fechaInicio,
    @RequestParam(required = false)
    @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime fechaFin,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
) {
    return historicoTicketAgenteService.filtrarHistoricoAgente(
        idAgente,
        estadoTicket,
        fechaInicio,
        fechaFin,
        PageRequest.of(page, size)
    );
}

//paginacion para historico ticket cliente
@GetMapping("/obtener-tickets-cliente-filtro")
public List<TicketDetalle> obtenerTicketsClienteFiltro(
        @RequestParam Long idCliente,
        @RequestParam(required = false) FiltroTicketsAgenteEnum estado,
        @RequestParam(required = false)
        @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
        LocalDate fecha
) {
    return ticketBusquedaService.obtenerTicketsClienteFiltro(idCliente, estado, fecha);
}

//filtrar ticket por departametno
@GetMapping("/tickets-departamento")
public List<TicketDetalle> obtenerTicketsPorDepartamento(
        @RequestParam Integer idDepartamento,
        @RequestParam(required = false) String estado,
        @RequestParam(required = false) TipoFechaEnum fechaOp,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate fecha
) {
    return ticketBusquedaService.obtenerTicketsPorDepartamentoFiltro(
            idDepartamento, estado, fechaOp, fecha
    );
}
}
