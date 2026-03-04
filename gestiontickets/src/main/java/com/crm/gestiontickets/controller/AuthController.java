package com.crm.gestiontickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.dto.ResumenAgente;
import com.crm.gestiontickets.dto.SolicitudLogin;
import com.crm.gestiontickets.service.AuthService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService agenteService;
    
    @PostMapping("/inicio-sesion")
    public ResumenAgente inicioSesion (@RequestBody SolicitudLogin credenciales){

        return agenteService.inicioSesion(credenciales);
    }

}
