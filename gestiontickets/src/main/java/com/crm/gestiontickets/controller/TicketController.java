package com.crm.gestiontickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.dto.IdTicketDTO;
import com.crm.gestiontickets.dto.TicketAperturaDTO;
import com.crm.gestiontickets.dto.TicketCreacionDTO;
import com.crm.gestiontickets.dto.TicketDetalleDTO;
import com.crm.gestiontickets.service.TicketService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/apertura")
    public IdTicketDTO aperturaTicket(@RequestBody TicketAperturaDTO ticketAperturaDTO){
        return ticketService.aperturaTicket(ticketAperturaDTO);
    }

    @PutMapping("/crear-ticket")
    public IdTicketDTO creacionTicket(@RequestBody TicketCreacionDTO ticketDetalleDTO){
        return ticketService.crearTicket(ticketDetalleDTO);
    }

    @GetMapping("/obtener-ticket")
    public TicketDetalleDTO obtenerTicket(@RequestParam String idTicket) {
        return ticketService.obtenerTicketDTO(idTicket);
    }
    
    
}
