/*Patron: estructural: Facade, delega la logica de negocios al service,
command:Cada metodo es una operacion especifica del sistema */
package com.crm.gestiontickets.agente.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.dto.DepartamentoDetalle;
import com.crm.gestiontickets.agente.entity.Departamento;
import com.crm.gestiontickets.agente.exception.DepartamentoNotFoundException;
import com.crm.gestiontickets.agente.repository.DepartamentoRepository;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public DepartamentoDetalle crearDepartamento(DepartamentoDetalle departamentoDTO) {

        Departamento departamento = new Departamento();

        departamento.setNombreDepartamento(departamentoDTO.getNombreDepartamento());
        departamento.setDescripcion(departamentoDTO.getDescripcion());
        departamento.setFechaCreacion(LocalDateTime.now());
        departamento.setActivo("S");

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

    // eliminar un departamento
    public void eliminarDepartamento(Integer idDepartamento) {
        Departamento departamento = departamentoRepository.findById(idDepartamento)
                .orElseThrow(() -> new DepartamentoNotFoundException(idDepartamento));

        departamentoRepository.delete(departamento);
    }

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

    // buscar departamento
    public Page<DepartamentoDetalle> buscarDepartamentos(String valor, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Departamento> departamentosPaginados = departamentoRepository.buscarPorCriterio(valor, pageable);
        return departamentosPaginados.map(this::convertirADTO);
    }

    private DepartamentoDetalle convertirADTO(Departamento dep) {
        DepartamentoDetalle dto = new DepartamentoDetalle();
        dto.setIdDepartamento(dep.getIdDepartamento());
        dto.setNombreDepartamento(dep.getNombreDepartamento());
        dto.setDescripcion(dep.getDescripcion());
        dto.setActivo(dep.getActivo());
        return dto;
    }

    // obtener los departamentos
    public Page<DepartamentoDetalle> obtenerDepartamentosActivos(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Departamento> departamentosPaginados = departamentoRepository.findByActivo("S", pageable);
        return departamentosPaginados.map(this::convertirADTO);
    }

}
