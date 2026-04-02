/*Patron: estructural: DTO, convierte las entidades Agente en objetosDTO */
package com.crm.gestiontickets.agente.mapper;

import org.springframework.stereotype.Component;

import com.crm.gestiontickets.agente.dto.AgenteDepartamento;
import com.crm.gestiontickets.agente.dto.AgenteDetalle;
import com.crm.gestiontickets.agente.entity.Agente;

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

    public AgenteDetalle mapearAgenteADetalle(Agente agente) {
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

}
