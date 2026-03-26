package com.crm.gestiontickets.agente.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.dto.RolDetalle;
import com.crm.gestiontickets.agente.entity.Rol;
import com.crm.gestiontickets.agente.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public RolDetalle crearRol(RolDetalle rolDTO){

        Rol rol = new Rol();

        rol.setNombre(rolDTO.getNombreRol());
        rol.setDescripcion(rolDTO.getDescripcionRol());
        rol.setFechaCreacion(LocalDateTime.now());
        rol.setActivo("S");

        rolRepository.save(rol);

        RolDetalle nuevoRol = new RolDetalle();
        nuevoRol.setIdRol(rol.getIdRol());
        nuevoRol.setNombreRol(rol.getNombre());
        nuevoRol.setDescripcionRol(rol.getDescripcion());

        return nuevoRol;
    }

    //obtener los roles
     public List<RolDetalle> obtenerRolesActivos() {
        List<Rol> roles = rolRepository.findByActivo("S");
        return roles.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());
    }

    private RolDetalle convertirADTO(Rol rol) {
     RolDetalle dto = new RolDetalle();
    dto.setIdRol(rol.getIdRol());
    dto.setNombreRol(rol.getNombre());      
    dto.setDescripcionRol(rol.getDescripcion()); 
    dto.setActivo(rol.getActivo());
    return dto;
    }
}