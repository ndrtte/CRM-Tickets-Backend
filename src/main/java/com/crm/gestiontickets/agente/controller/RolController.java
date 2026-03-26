/*Patron: estructural: Facade, permite manejar olas operaciones relaiconadas con los roles */
package com.crm.gestiontickets.agente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.agente.dto.RolDetalle;
import com.crm.gestiontickets.agente.service.RolService;

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