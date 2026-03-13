package com.crm.gestiontickets.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.DepartamentoDetalle;
import com.crm.gestiontickets.entity.Departamento;
import com.crm.gestiontickets.exception.DepartamentoNotFoundException;
import com.crm.gestiontickets.repository.DepartamentoRepository;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public DepartamentoDetalle crearDepartamento(DepartamentoDetalle departamentoDTO){

        Departamento departamento = new Departamento();

        departamento.setNombreDepartamento(departamentoDTO.getNombreDepartamento());
        departamento.setDescripcion(departamentoDTO.getDescripcion());
        departamento.setFechaCreacion(LocalDateTime.now());

        departamentoRepository.save(departamento);

        DepartamentoDetalle nuevoDepartamento = new DepartamentoDetalle();

        nuevoDepartamento.setIdDepartamento(departamento.getIdDepartamento());
        nuevoDepartamento.setNombreDepartamento(departamento.getNombreDepartamento());
        nuevoDepartamento.setDescripcion(departamento.getDescripcion());

        return nuevoDepartamento;
    }

     // Actualizar un departamento
      public DepartamentoDetalle actualizarDepartamento(DepartamentoDetalle departamentoDTO) {

        Departamento departamento = departamentoRepository.findById(departamentoDTO.getIdDepartamento())
                .orElseThrow(() -> new DepartamentoNotFoundException(departamentoDTO.getIdDepartamento()));

        departamento.setNombreDepartamento(departamentoDTO.getNombreDepartamento());
        departamento.setDescripcion(departamentoDTO.getDescripcion());
        departamento.setFechaActualizacion(LocalDateTime.now());

        departamentoRepository.save(departamento);

        DepartamentoDetalle dto = new DepartamentoDetalle();
        dto.setIdDepartamento(departamento.getIdDepartamento());
        dto.setNombreDepartamento(departamento.getNombreDepartamento());
        dto.setDescripcion(departamento.getDescripcion());
        dto.setFechaCreacion(departamento.getFechaCreacion());
        dto.setFechaActualizacion(departamento.getFechaActualizacion());

        return dto;
    }

    //eliminar un departamento
    public void eliminarDepartamento(Integer idDepartamento) {
        Departamento departamento = departamentoRepository.findById(idDepartamento)
                .orElseThrow(() -> new DepartamentoNotFoundException(idDepartamento));

        departamentoRepository.delete(departamento);
    }

    //desactivar o activar un departamento
    public Departamento bloquearDepartamento(Integer idDepartamento) {

    Departamento departamento = departamentoRepository.findById(idDepartamento)
            .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

    if ("S".equals(departamento.getActivo())) {
        departamento.setActivo("N"); // bloquear
    } else {
        departamento.setActivo("S"); // desbloquear
    }

    departamento.setFechaActualizacion(LocalDateTime.now());

    return departamentoRepository.save(departamento);
}
}
