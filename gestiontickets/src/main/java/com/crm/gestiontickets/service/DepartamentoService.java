package com.crm.gestiontickets.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.DepartamentoDetalle;
import com.crm.gestiontickets.entity.Departamento;
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

}