package com.crm.gestiontickets.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crm.gestiontickets.ticket.dto.CrearFlujoDTO;
import com.crm.gestiontickets.ticket.dto.EtapaDTO;
import com.crm.gestiontickets.ticket.dto.EtapaTicket;
import com.crm.gestiontickets.ticket.service.FlujoService;

@RestController
@RequestMapping("/flujo")
public class FlujoController {

    @Autowired
    private FlujoService flujoService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearFlujo(@RequestBody CrearFlujoDTO dto) {
        flujoService.crearFlujo(dto);
        return ResponseEntity.ok("Flujo creado correctamente");
    }

    @PutMapping("/estado/{idFlujo}")
    public ResponseEntity<String> cambiarEstadoFlujo(@PathVariable Integer idFlujo) {

        flujoService.cambiarEstadoFlujo(idFlujo);

        return ResponseEntity.ok("Estado del flujo actualizado correctamente");
    }

    @GetMapping("/{idFlujo}/etapas")
    public ResponseEntity<List<EtapaTicket>> obtenerEtapas(@PathVariable Integer idFlujo) {
        return ResponseEntity.ok(flujoService.obtenerEtapasPorFlujo(idFlujo));
    }

    @PutMapping("/paso/{idPaso}/estado")
    public ResponseEntity<String> cambiarEstadoPaso(@PathVariable Integer idPaso) {

        flujoService.cambiarEstadoPaso(idPaso);

        return ResponseEntity.ok("Estado de la etapa actualizado correctamente");
    }

    @PutMapping("/paso/{idPaso}")
    public ResponseEntity<String> actualizarPaso(
            @PathVariable Integer idPaso,
            @RequestBody EtapaDTO dto) {

        flujoService.actualizarPaso(idPaso, dto);

        return ResponseEntity.ok("Etapa actualizada correctamente");
    }
}