package com.crm.gestiontickets.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.AgenteDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Departamento;
import com.crm.gestiontickets.entity.Rol;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.DepartamentoRepository;
import com.crm.gestiontickets.repository.RolRepository;

@Service
public class AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private RolRepository rolRepository;

    public AgenteDetalle crearAgente(AgenteDetalle agenteDTO){

        Departamento departamento = departamentoRepository.findById(agenteDTO.getIdDepartamento())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

        Rol rol = rolRepository.findById(agenteDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Agente agente = new Agente();
        agente.setNombre(agenteDTO.getNombre());
        agente.setApellido(agenteDTO.getApellido());
        agente.setUsuario(agenteDTO.getUsuario());
        agente.setContrasenia(agenteDTO.getContrasenia());
        agente.setActivo(agenteDTO.getActivo());
        agente.setDepartamento(departamento);
        agente.setRol(rol);
        agente.setFechaCreacion(LocalDateTime.now());

        agenteRepository.save(agente);

        AgenteDetalle nuevoAgente = new AgenteDetalle();
        nuevoAgente.setIdAgente(agente.getIdAgente());
        nuevoAgente.setNombre(agente.getNombre());
        nuevoAgente.setApellido(agente.getApellido());
        nuevoAgente.setUsuario(agente.getUsuario());
        nuevoAgente.setContrasenia(agente.getContrasenia());
        nuevoAgente.setActivo(agente.getActivo());
        nuevoAgente.setIdDepartamento(departamento.getIdDepartamento());
        nuevoAgente.setIdRol(rol.getIdRol());        

        return nuevoAgente;
    }

     @SuppressWarnings("unchecked")
    public List<AgenteDetalle> buscarAgentes(String criterio) {

        List<Agente> agentes;

        // Intentar buscar por idAgente si es número
        try {
            Integer id = Integer.parseInt(criterio);
            agentes = agenteRepository.findByIdAgente(id);
        } catch (NumberFormatException e) {
            // Si no es número, buscar por nombre o usuario
            agentes = agenteRepository.findByNombreContainingIgnoreCase(criterio);
            if (agentes.isEmpty()) {
                agentes = (List<Agente>) agenteRepository.findByUsuario(criterio);
            }
        }

        // Convertir entidades a DTO
        return agentes.stream().map(agente -> {
            AgenteDetalle dto = new AgenteDetalle();
            dto.setIdAgente(agente.getIdAgente());
            dto.setNombre(agente.getNombre());
            dto.setApellido(agente.getApellido());
            dto.setUsuario(agente.getUsuario());
            dto.setActivo(agente.getActivo());
            dto.setIdDepartamento(agente.getDepartamento().getIdDepartamento());
            // Si quieres incluir rol más adelante, se puede agregar
            return dto;
        }).collect(Collectors.toList());
    }
}