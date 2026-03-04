package com.crm.gestiontickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.SolicitudLogin;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.repository.AgenteRepository;

@Service
public class AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;
    
    public String inicioSesion (SolicitudLogin credenciales){
        Agente agente = agenteRepository.findByUsuario(credenciales.getUsuario());

        if(agente == null){
            return "";
        }

        

        return "";
    }

}
