package com.crm.gestiontickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.SolicitudLogin;
import com.crm.gestiontickets.dto.agente.ResumenAgente;
import com.crm.gestiontickets.service.AuthService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/inicio-sesion")
    public Respuesta<ResumenAgente> inicioSesion(@RequestBody SolicitudLogin credenciales) {
        return authService.inicioSesion(credenciales);
    }
}
