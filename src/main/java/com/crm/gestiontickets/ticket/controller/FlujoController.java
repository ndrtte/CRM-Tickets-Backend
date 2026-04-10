package com.crm.gestiontickets.ticket.controller;

import java.util.List;

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

    //obtener lista de flujos
    @GetMapping
    public List<FlujoDetalle> obtenerFlujos() {
        return flujoService.obtenerFlujos();
    }

    //etapas de un flujo
    @GetMapping("/{idFlujo}")
    public FlujoDetalle obtenerFlujoPorId(@PathVariable Integer idFlujo) {
    return flujoService.obtenerFlujoPorId(idFlujo);
}
    //editar un flujo
    @PutMapping("/editar/{idFlujo}")
    public FlujoDetalle actualizarFlujo(
        @PathVariable Integer idFlujo,
        @RequestBody FlujoDetalle dto) {

    return flujoService.actualizarFlujo(idFlujo, dto);
}
}