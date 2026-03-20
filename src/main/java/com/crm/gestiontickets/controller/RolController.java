package com.crm.gestiontickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.crm.gestiontickets.dto.agente.RolDetalle;
import com.crm.gestiontickets.service.RolService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/roles")

public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping("/crear-rol")
    public RolDetalle crearRol(@RequestBody RolDetalle rol) {
        return rolService.crearRol(rol);
    }

    //obtener los roles
    @GetMapping("/obtener-roles")
    public List<RolDetalle> obtenerRoles() {
        return rolService.obtenerRolesActivos();
    }

}