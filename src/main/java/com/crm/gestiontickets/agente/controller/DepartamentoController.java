package com.crm.gestiontickets.agente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.agente.dto.DepartamentoDetalle;
import com.crm.gestiontickets.agente.entity.Departamento;
import com.crm.gestiontickets.agente.service.DepartamentoService;

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

    //Endpoint para buscar un departamento
    @GetMapping("/buscar")
    public List<DepartamentoDetalle> buscarDepartamentos(@RequestParam("criterio") String criterio) {
        return departamentoService.buscarDepartamentos(criterio);
    }

    //optener los departamentos
   // Endpoint para obtener departamentos activos
    @GetMapping("/departamentos-activos")
    public List<DepartamentoDetalle> obtenerDepartamentosActivos() {
        return departamentoService.obtenerDepartamentosActivos();
    }

}