package com.crm.gestiontickets.agente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.agente.dto.AgenteDepartamento;
import com.crm.gestiontickets.agente.dto.AgenteDetalle;
import com.crm.gestiontickets.agente.service.AgenteBusquedaService;
import com.crm.gestiontickets.agente.service.AgenteService;



@CrossOrigin("*")
@RestController
@RequestMapping("/api/agentes")

public class AgenteController {
    @Autowired
    private AgenteService agenteService;
    
    @Autowired
    private AgenteBusquedaService agenteBusquedaService;

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

    @GetMapping("/obtener/agente-departamento")
    public List<AgenteDepartamento> getMethodName(@RequestParam Integer idDepartamento) {
        return agenteBusquedaService.agentePorDepartamento(idDepartamento);
    }
    

}
