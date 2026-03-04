package com.crm.gestiontickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.crm.gestiontickets.dto.SolicitudLogin;
import com.crm.gestiontickets.service.AgenteService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/agente")
public class AgenteController {

    @Autowired
    private AgenteService agenteService;
    
    @PostMapping("/inicio-sesion")
    public String inicioSesion (@RequestBody SolicitudLogin credenciales){
        return agenteService.inicioSesion(credenciales);
    }

}
