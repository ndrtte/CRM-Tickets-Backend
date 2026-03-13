package com.crm.gestiontickets.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        // valida que el departamento exista
        Departamento departamento = departamentoRepository.findById(agenteDTO.getIdDepartamento())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

        // valida que el rol exista
        Rol rol = rolRepository.findById(agenteDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Crear  Agente
        Agente agente = new Agente();
        agente.setNombre(agenteDTO.getNombre());
        agente.setApellido(agenteDTO.getApellido());
        agente.setUsuario(agenteDTO.getUsuario());
        agente.setContrasenia(agenteDTO.getContrasenia());
        agente.setActivo(agenteDTO.getActivo());
        agente.setDepartamento(departamento);
        agente.setRol(rol);
        agente.setFechaCreacion(LocalDateTime.now());

        // Guardar en la BD
        agenteRepository.save(agente);

        // Devolver DTO con datos guardados
        return convertirADTO(agente);
    }


    public List<AgenteDetalle> obtenerAgentes(String valorBusqueda) {
        List<Agente> listaAgentes = agenteRepository.buscarPorCriterio(valorBusqueda);        List<AgenteDetalle> listaAgenteDetalles = new ArrayList<>();
        for (Agente agente : listaAgentes) {
            AgenteDetalle dto = new AgenteDetalle();
            dto.setIdAgente(agente.getIdAgente());
            dto.setNombre(agente.getNombre());
            dto.setApellido(agente.getApellido());
            dto.setUsuario(agente.getUsuario());
            dto.setActivo(agente.getActivo());
            dto.setIdDepartamento(agente.getDepartamento().getIdDepartamento());
            dto.setIdRol(agente.getRol().getIdRol());

            listaAgenteDetalles.add(dto);
        }
        
        return listaAgenteDetalles;

       
    }

    public AgenteDetalle editarAgente(Integer idAgente, AgenteDetalle agenteDTO) {
        // Validar ID
        if (idAgente == null) {
            throw new IllegalArgumentException("El ID del agente no puede ser null");
        }

        // Buscar el agente en BD
        Agente agente = agenteRepository.findById(idAgente)
                .orElseThrow(() -> new RuntimeException("Agente no encontrado"));

        // Buscar departamento y rol
        Departamento departamento = departamentoRepository.findById(agenteDTO.getIdDepartamento())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

        Rol rol = rolRepository.findById(agenteDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Actualizar los campos
        agente.setNombre(agenteDTO.getNombre());
        agente.setApellido(agenteDTO.getApellido());
        agente.setUsuario(agenteDTO.getUsuario());
        agente.setContrasenia(agenteDTO.getContrasenia());
        agente.setActivo(agenteDTO.getActivo());
        agente.setDepartamento(departamento);
        agente.setRol(rol);
        agente.setFechaActualizacion(LocalDateTime.now());

        agenteRepository.save(agente);

        AgenteDetalle actualizado = new AgenteDetalle();
        actualizado.setIdAgente(agente.getIdAgente());
        actualizado.setNombre(agente.getNombre());
        actualizado.setApellido(agente.getApellido());
        actualizado.setUsuario(agente.getUsuario());
        actualizado.setContrasenia(agente.getContrasenia());
        actualizado.setActivo(agente.getActivo());
        actualizado.setIdDepartamento(departamento.getIdDepartamento());
        actualizado.setIdRol(rol.getIdRol());

        return actualizado;
    }

    private AgenteDetalle convertirADTO(Agente agente) {
        AgenteDetalle dto = new AgenteDetalle();
        dto.setIdAgente(agente.getIdAgente());
        dto.setNombre(agente.getNombre());
        dto.setApellido(agente.getApellido());
        dto.setUsuario(agente.getUsuario());
        dto.setContrasenia(agente.getContrasenia());
        dto.setActivo(agente.getActivo());
        dto.setIdDepartamento(agente.getDepartamento().getIdDepartamento());
        dto.setIdRol(agente.getRol().getIdRol());
        return dto;
    }

    public AgenteDetalle bloquearAgente(Integer idAgente) {
        // Validar ID
        if (idAgente == null) {
            throw new IllegalArgumentException("El ID del agente no puede ser null");
        }

        // Buscar el agente en BD
        Agente agente = agenteRepository.findById(idAgente)
                .orElseThrow(() -> new RuntimeException("Agente no encontrado"));

        // Bloquear el agente
        if ("S".equals(agente.getActivo())) {
         agente.setActivo("N"); // bloquear
        } else {
        agente.setActivo("S"); // desbloquear
        }
        agente.setFechaActualizacion(LocalDateTime.now());

        agenteRepository.save(agente);

        //retornar el DTO actualizado
        AgenteDetalle dto = new AgenteDetalle();
        dto.setIdAgente(agente.getIdAgente());
        dto.setNombre(agente.getNombre());
        dto.setApellido(agente.getApellido());
        dto.setUsuario(agente.getUsuario());
        dto.setActivo(agente.getActivo());
        dto.setIdDepartamento(agente.getDepartamento().getIdDepartamento());
        dto.setIdRol(agente.getRol().getIdRol());


        return convertirADTO(agente);
    }
}