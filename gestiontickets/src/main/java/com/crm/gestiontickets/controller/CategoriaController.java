package com.crm.gestiontickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.dto.CategoriaDetalleDTO;
import com.crm.gestiontickets.service.CategoriaService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/obtener-categorias")
    public List<CategoriaDetalleDTO> obtenerCategorias (){
        return categoriaService.obtenerCategorias();
    }
    
}
