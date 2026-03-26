package com.crm.gestiontickets.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.agente.dto.ResumenAgente;
import com.crm.gestiontickets.auth.dto.SolicitudLogin;
import com.crm.gestiontickets.auth.service.AuthService;
import com.crm.gestiontickets.shared.dto.Respuesta;

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
