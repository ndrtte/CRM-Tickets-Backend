package com.crm.gestiontickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.crm.gestiontickets.dto.AgenteDetalle;
import com.crm.gestiontickets.service.AgenteService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/agentes")

public class AgenteController {
    @Autowired
    private AgenteService agenteService;  

    @PostMapping("/crear-agente")
    public AgenteDetalle crearAgente(@RequestBody AgenteDetalle agente) {
        return agenteService.crearAgente(agente);
    }

    //endpoint para buscar agentes por nombre, usuario o id_agente
    @GetMapping("/buscar")
    public List<AgenteDetalle> buscarAgentes(@RequestParam("criterio") String criterio) {
        return agenteService.buscarAgentes(criterio);
    }

    //ennpoint para editar un agente
    @PutMapping("/editar-agente/{id}")
    public AgenteDetalle editarAgente(@PathVariable("id") Integer id, @RequestBody AgenteDetalle agente) {
    return agenteService.editarAgente(id, agente);
    }

    //endpoint para bloquear un agente
    @PutMapping("/bloquear-agente/{id}")
    public AgenteDetalle bloquearAgente(@PathVariable("id") Integer id) {
        return agenteService.bloquearAgente(id);
    }

}
