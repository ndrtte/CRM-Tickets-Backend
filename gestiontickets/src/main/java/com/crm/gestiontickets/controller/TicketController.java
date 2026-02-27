package com.crm.gestiontickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.dto.TicketAperturaDTO;
import com.crm.gestiontickets.service.TicketService;

import org.springframework.web.bind.annotation.PostMapping;

import com.crm.gestiontickets.dto.TicketDetalleDTO;
import org.springframework.web.bind.annotation.PutMapping;



@CrossOrigin("*")
@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/apertura")
    public String aperturaTicket(@RequestBody TicketAperturaDTO ticketAperturaDTO){
        return ticketService.aperturaTicket(ticketAperturaDTO);
    }

    @PutMapping("/crear-ticket")
    public String creacionTicket(@RequestBody TicketDetalleDTO ticketDetalleDTO){
        return ticketService.crearTicket(ticketDetalleDTO);
    }

}
