/*Patron: estructural: Facade, expone los endpoint REST, simplifica la interaccion de con el cliente,
delega la logica de negocios al service */
package com.crm.gestiontickets.agente.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.agente.dto.AgenteDepartamento;
import com.crm.gestiontickets.agente.dto.AgenteDetalle;
import com.crm.gestiontickets.agente.service.AgenteBusquedaService;
import com.crm.gestiontickets.agente.service.AgenteService;
import com.crm.gestiontickets.agente.service.TicketBusquedaService;
import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.enums.TipoFechaEnum;

import com.crm.gestiontickets.agente.enums.EstadoTicketAgenteEnum;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/agentes")

public class AgenteController {
    @Autowired
    private AgenteService agenteService;
    
    @Autowired
    private AgenteBusquedaService agenteBusquedaService;
    
    @Autowired
    private TicketBusquedaService ticketBusquedaService;

    @PostMapping("/crear-agente")
    public AgenteDetalle crearAgente(@RequestBody AgenteDetalle agente) {
        return agenteService.crearAgente(agente);
    }

    //endpoint para buscar agentes por nombre, usuario o id_agente
    @GetMapping("/buscar")
    public List<AgenteDetalle> buscarAgentes(@RequestParam("criterio") String criterio) {
    return agenteService.buscarAgentes(criterio);
    }

    //ennpoint para editar un agente
    @PutMapping("/editar-agente/{id}")
    public AgenteDetalle editarAgente(@PathVariable("id") Integer id, @RequestBody AgenteDetalle agente) {
    return agenteService.editarAgente(id, agente);
    }

    //endpoint para bloquear un agente
    @PutMapping("/bloquear-agente/{id}")
    public AgenteDetalle bloquearAgente(@PathVariable("id") Integer id) {
        return agenteService.bloquearAgente(id);
    }

    @GetMapping("/obtener/agente-departamento")
    public List<AgenteDepartamento> getMethodName(@RequestParam Integer idDepartamento) {
        return agenteBusquedaService.agentePorDepartamento(idDepartamento);
    }
    
    @GetMapping("/obtener-tickets-agente")
public List<TicketDetalle> obtenerTicketsPorAgente(
        @RequestParam Integer idAgente,
        @RequestParam(required = false) EstadoTicketAgenteEnum filtroEstado,
        @RequestParam(required = false) TipoFechaEnum fechaOp,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate fecha
) {
    return ticketBusquedaService.obtenerTicketsPorAgente(
            idAgente,
            filtroEstado,
            fechaOp,
            fecha
    );
}

}
