package com.crm.gestiontickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.ticket.TicketApertura;
import com.crm.gestiontickets.dto.ticket.TicketAvanzarEtapa;
import com.crm.gestiontickets.dto.ticket.TicketCreacion;
import com.crm.gestiontickets.dto.ticket.TicketDetalle;
import com.crm.gestiontickets.dto.ticket.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.dto.ticket.TicketEtapaDetalle;
import com.crm.gestiontickets.dto.ticket.TicketPasoResponse;
import com.crm.gestiontickets.enums.FiltroTicketsAgenteEnum;
import com.crm.gestiontickets.service.ticket.TicketAperturaService;
import com.crm.gestiontickets.service.ticket.TicketBusquedaService;
import com.crm.gestiontickets.service.ticket.TicketFlujoService;

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

    @GetMapping("/obtener-tickets-agente")
    public List<TicketEtapaAgenteDetalle> obtenerTicketsAgente(@RequestParam Integer idAgente, FiltroTicketsAgenteEnum filtroEstado) {
        return ticketBusquedaService.obtenerTicketsAgente(idAgente, filtroEstado);
    }
}
