package com.crm.gestiontickets.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //obtener lista de flujos
    @GetMapping
    public List<FlujoDetalle> obtenerFlujos() {
        return flujoService.obtenerFlujos();
    }
}