package com.crm.gestiontickets.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.agente.RolDetalle;
import com.crm.gestiontickets.entity.Rol;
import com.crm.gestiontickets.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public RolDetalle crearRol(RolDetalle rolDTO){

        Rol rol = new Rol();

        rol.setNombre(rolDTO.getNombreRol());
        rol.setDescripcion(rolDTO.getDescripcionRol());
        rol.setFechaCreacion(LocalDateTime.now());

        rolRepository.save(rol);

        RolDetalle nuevoRol = new RolDetalle();
        nuevoRol.setIdRol(rol.getIdRol());
        nuevoRol.setNombreRol(rol.getNombre());
        nuevoRol.setDescripcionRol(rol.getDescripcion());
        nuevoRol.setActivo(rol.getActivo());

        return nuevoRol;
    }

   public List<RolDetalle> obtenerRolesActivos() {
    List<Rol> roles = rolRepository.findByActivo("S");
    return roles.stream()
                .map(rol -> {
                    RolDetalle dto = new RolDetalle();
                    dto.setIdRol(rol.getIdRol());
                    dto.setNombreRol(rol.getNombre());
                    dto.setDescripcionRol(rol.getDescripcion());
                    dto.setActivo(rol.getActivo());
                    return dto;
                })
                .collect(Collectors.toList());
}
}