package com.crm.gestiontickets.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.crm.gestiontickets.ticket.dto.FlujoDetalle;
import com.crm.gestiontickets.ticket.service.FlujoService;

@RestController
@RequestMapping("/api/flujos")
public class FlujoController {

    @Autowired
    private FlujoService flujoService;
    
    //crear flujo 
    @PostMapping("/crear")
    public FlujoDetalle crearFlujo(@RequestBody FlujoDetalle dto) {
        return flujoService.crearFlujo(dto);
    }

    //habilitar o deshabilitar flujo
    @PutMapping("/estado/{idFlujo}")
public FlujoDetalle cambiarEstadoFlujo(@PathVariable Integer idFlujo) {
    return flujoService.cambiarEstadoFlujo(idFlujo);
}
}