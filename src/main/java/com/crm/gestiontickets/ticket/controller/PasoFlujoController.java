package com.crm.gestiontickets.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.IdPasoFlujo;
import com.crm.gestiontickets.ticket.dto.PasoFlujoDetalle;
import com.crm.gestiontickets.ticket.service.PasoFlujoService;



@RestController
@RequestMapping("api/paso-flujo")
public class PasoFlujoController {

    @Autowired
    public PasoFlujoService pasoFlujoService;
    
    @GetMapping("/obtener/paso-actual")
    public Respuesta<IdPasoFlujo> obtenerPasoActual(@RequestParam String idTicket){
        return pasoFlujoService.obtenerPasoActual(idTicket);
    }
//habilitar o desabilitar una etapa del flujo
    @PutMapping("/estado/{idPaso}")
    public Respuesta<PasoFlujoDetalle> cambiarEstado(@PathVariable Integer idPaso) {
    return pasoFlujoService.cambiarEstado(idPaso);
    }
    
}
