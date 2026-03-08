package com.crm.gestiontickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.crm.gestiontickets.dto.DepartamentoDetalle;
import com.crm.gestiontickets.service.DepartamentoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @PostMapping("/crear-departamento")
    public DepartamentoDetalle crearDepartamento(@RequestBody DepartamentoDetalle departamento){
        return departamentoService.crearDepartamento(departamento);
    }

}