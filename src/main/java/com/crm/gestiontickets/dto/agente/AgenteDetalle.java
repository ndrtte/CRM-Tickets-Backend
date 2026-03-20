package com.crm.gestiontickets.dto.agente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AgenteDetalle {

    private Integer idAgente;
    private String nombre;
    private String apellido;
    private String usuario;
    private String contrasenia;
    private String activo; 
    private Integer idDepartamento; 
    private Integer idRol;
}