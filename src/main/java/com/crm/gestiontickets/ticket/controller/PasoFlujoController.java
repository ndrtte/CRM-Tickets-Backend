package com.crm.gestiontickets.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.IdPasoFlujo;
import com.crm.gestiontickets.ticket.service.PasoFlujoService;



@RestController
@RequestMapping("api/paso-flujo")
@CrossOrigin("*")
public class PasoFlujoController {

    @Autowired
    public PasoFlujoService pasoFlujoService;
    
    @GetMapping("/obtener/paso-actual")
    public Respuesta<IdPasoFlujo> obtenerPasoActual(@RequestParam String idTicket){
        return pasoFlujoService.obtenerPasoActual(idTicket);
    }
    
}
