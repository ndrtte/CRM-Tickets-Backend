package com.crm.gestiontickets.agente.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter

public class RolDetalle {

    private Integer idRol;
    private String nombreRol;
    private String descripcionRol;
    private String activo;

}