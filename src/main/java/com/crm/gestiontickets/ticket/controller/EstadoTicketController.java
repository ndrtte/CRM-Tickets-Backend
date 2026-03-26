/* Patrón: estructural: Facade, simplifica la interacción con el cliente y delega la
logica al EstadoTicketService */

package com.crm.gestiontickets.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.ticket.dto.EstadoTicketDetalle;
import com.crm.gestiontickets.ticket.service.EstadoTicketService;


@CrossOrigin("*")
@RestController
@RequestMapping("api/estados-ticket")
public class EstadoTicketController {

    @Autowired
    private EstadoTicketService estadoService;
    
    @GetMapping("/obtener-estados")
    public List<EstadoTicketDetalle> obtenerEstadosTicket(){
        return estadoService.obtenerEstadosTicket();
    }
}
