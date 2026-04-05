package com.crm.gestiontickets.flujos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.flujos.dto.CrearFlujoDTO;
import com.crm.gestiontickets.flujos.service.FlujoService;

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
}