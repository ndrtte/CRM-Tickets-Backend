package com.crm.gestiontickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.ResumenAgente;
import com.crm.gestiontickets.dto.SolicitudLogin;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.repository.AgenteRepository;

@Service
public class AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;
    
    public ResumenAgente inicioSesion (SolicitudLogin credenciales){
        Agente agente = agenteRepository.findByUsuario(credenciales.getUsuario());

        if(agente == null){
            return new ResumenAgente();
        }

        String contrasenia = credenciales.getContrasenia();
        
        if(!contrasenia.equals(agente.getContrasenia())){
            return new ResumenAgente();
        }

        String nombre = agente.getNombre()+" "+agente.getApellido();
        Integer idDepartamento = agente.getDepartamento().getIdDepartamento();
        String departamento = agente.getDepartamento().getNombreDepartamento();
        Integer idRol = agente.getRol().getIdRol();
        String rol = agente.getRol().getNombre();

        ResumenAgente agenteDTO = new ResumenAgente();
        agenteDTO.setIdAgente(agente.getIdAgente());
        agenteDTO.setNombre(nombre);
        agenteDTO.setIdDepartamento(idDepartamento);
        agenteDTO.setDepartamento(departamento);
        agenteDTO.setUsuario(agente.getUsuario());
        agenteDTO.setIdRol(idRol);
        agenteDTO.setRol(rol);

        return agenteDTO;
    }

}
