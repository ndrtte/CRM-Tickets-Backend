package com.crm.gestiontickets.mapper;

import org.springframework.stereotype.Component;

import com.crm.gestiontickets.dto.agente.AgenteDepartamento;
import com.crm.gestiontickets.entity.Agente;

@Component
public class AgenteMapper {

    public AgenteDepartamento mapearAgenteDepartamento(Agente agente) {
        AgenteDepartamento detalle = new AgenteDepartamento();

        Integer idAgente = agente.getIdAgente();
        String nombre = agente.getNombre() + " " + agente.getApellido();
        String usuario = agente.getUsuario();
        String activo = agente.getActivo();
        Integer idRol = agente.getRol().getIdRol();
        String rol = agente.getRol().getNombre();
        Integer cantidadTickets = agente.getListaTickets().size();

        detalle.setIdAgente(idAgente);
        detalle.setNombre(nombre);
        detalle.setUsuario(usuario);
        detalle.setActivo(activo);
        detalle.setIdRol(idRol);
        detalle.setRol(rol);
        detalle.setCantidadTickets(cantidadTickets);

        return detalle;
    }

}
