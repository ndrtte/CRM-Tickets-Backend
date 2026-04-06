package com.crm.gestiontickets.flujos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crm.gestiontickets.flujos.dto.CrearFlujoDTO;
import com.crm.gestiontickets.flujos.dto.EtapaDTO;
import com.crm.gestiontickets.flujos.dto.EtapaDetalleDTO;
import com.crm.gestiontickets.flujos.service.FlujoService;

@RestController
@RequestMapping("/flujo")
public class FlujoController {

    @Autowired
    private FlujoService flujoService;


    // crear flujo
    @PostMapping("/crear")
    public ResponseEntity<String> crearFlujo(@RequestBody CrearFlujoDTO dto) {

        flujoService.crearFlujo(dto);

        return ResponseEntity.ok("Flujo creado correctamente");
    }

    // desabilitar un flujo
    @PutMapping("/estado/{idFlujo}")
public ResponseEntity<String> cambiarEstadoFlujo(@PathVariable Integer idFlujo) {

    flujoService.cambiarEstadoPaso(idFlujo);

    return ResponseEntity.ok("Estado del flujo actualizado correctamente");
}

    // obtener las etapas de un flujo
    @GetMapping("/{idFlujo}/etapas")
    public ResponseEntity<List<EtapaDetalleDTO>> obtenerEtapas(@PathVariable Integer idFlujo) {

        return ResponseEntity.ok(flujoService.obtenerEtapasPorFlujo(idFlujo));
    }

    // habilitar / deshabilitar una etapa
    @PutMapping("/paso/{idPaso}/estado")
    public ResponseEntity<String> cambiarEstadoPaso(@PathVariable Integer idPaso) {

        flujoService.cambiarEstadoPaso(idPaso);

        return ResponseEntity.ok("Estado de la etapa actualizado correctamente");
    }

    //editar una etapa
    @PutMapping("/paso/{idPaso}")
    public ResponseEntity<String> actualizarPaso(
        @PathVariable Integer idPaso,
        @RequestBody EtapaDTO dto) {

    flujoService.actualizarPaso(idPaso, dto);

    return ResponseEntity.ok("Etapa actualizada correctamente");
}
}