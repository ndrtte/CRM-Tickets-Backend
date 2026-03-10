package com.crm.gestiontickets.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.AgenteDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Departamento;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.DepartamentoRepository;

@Service
public class AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public AgenteDetalle crearAgente(AgenteDetalle agenteDTO){

        // Verificar que el departamento existe
        Departamento departamento = departamentoRepository.findById(agenteDTO.getIdDepartamento())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

        // Crear entidad Agente
        Agente agente = new Agente();
        agente.setNombre(agenteDTO.getNombre());
        agente.setApellido(agenteDTO.getApellido());
        agente.setUsuario(agenteDTO.getUsuario());
        agente.setContrasenia(agenteDTO.getContrasenia());
        agente.setActivo(agenteDTO.getActivo());
        agente.setDepartamento(departamento); // Asignar departamento
        agente.setFechaCreacion(LocalDateTime.now());

        // Guardar en DB
        agenteRepository.save(agente);

        // Devolver DTO con datos del nuevo agente
        AgenteDetalle nuevoAgente = new AgenteDetalle();
        nuevoAgente.setIdAgente(agente.getIdAgente());
        nuevoAgente.setNombre(agente.getNombre());
        nuevoAgente.setApellido(agente.getApellido());
        nuevoAgente.setUsuario(agente.getUsuario());
        nuevoAgente.setContrasenia(agente.getContrasenia());
        nuevoAgente.setActivo(agente.getActivo());
        nuevoAgente.setIdDepartamento(departamento.getIdDepartamento());

        

        return nuevoAgente;
    }
}