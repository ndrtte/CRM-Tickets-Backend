package com.crm.gestiontickets.controller;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crm.gestiontickets.dto.DepartamentoDetalle;
import com.crm.gestiontickets.entity.Departamento;
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

    //Endpoint para actualizar departamento
    @PutMapping("/actualizar-departamento")
    public DepartamentoDetalle actualizarDepartamento(@RequestBody DepartamentoDetalle departamento) {
        return departamentoService.actualizarDepartamento(departamento);
    }

    //enpoint para eliminar departamento
    @DeleteMapping("/eliminar-departamento/{id}")
    public String eliminarDepartamento(@PathVariable Integer id) {
        departamentoService.eliminarDepartamento(id);
        return "Departamento con ID " + id + " eliminado exitosamente.";
    }

    //endpoint para bloquear o desbloquear departamento
    @PutMapping("/bloquear/{id}")
    public ResponseEntity<Departamento> bloquearDepartamento(@PathVariable Integer id){

    Departamento departamento = departamentoService.bloquearDepartamento(id);

    return ResponseEntity.ok(departamento);
}

}