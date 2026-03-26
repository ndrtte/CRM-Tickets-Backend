package com.crm.gestiontickets.agente.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgenteDepartamento {

    private Integer idAgente;
    private String nombre;
    private String usuario;
    private String activo;     
    private Integer idRol;
    private String rol;
    private Integer cantidadTickets;

}
