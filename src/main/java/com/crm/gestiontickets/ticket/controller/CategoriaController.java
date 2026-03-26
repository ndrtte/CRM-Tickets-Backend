/* Patrón: estructural: Facade, expone endpoints REST y delega la lógica al service */

package com.crm.gestiontickets.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.ticket.dto.CategoriaDetalle;
import com.crm.gestiontickets.ticket.service.CategoriaService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/obtener-categorias")
    public List<CategoriaDetalle> obtenerCategorias (){
        return categoriaService.obtenerCategorias();
    }

      //crear categoria
    @PostMapping("/crear-categoria")
    public CategoriaDetalle crearCategoria(@RequestBody CategoriaDetalle dto) {
    
        return categoriaService.crearCategoria(dto);
    }

    //actualizar cateoria
    @PutMapping("/actualizar-categoria/{id}")
    public CategoriaDetalle actualizarCategoria(
            @PathVariable Integer id,
            @RequestBody CategoriaDetalle dto) {

        return categoriaService.actualizarCategoria(id, dto);
    }
    
    //activar o desactivar categoria
    @PutMapping("/estado-categoria/{id}")
   public CategoriaDetalle cambiarEstadoCategoria(@PathVariable Integer id) {
        return categoriaService.cambiarEstadoCategoria(id);
    }
    
}
