package com.crm.gestiontickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.dto.ticket.EstadoTicketDetalle;
import com.crm.gestiontickets.service.ticket.EstadoTicketService;
import org.springframework.web.bind.annotation.GetMapping;


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
