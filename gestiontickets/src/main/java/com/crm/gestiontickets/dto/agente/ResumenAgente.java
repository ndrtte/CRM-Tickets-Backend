package com.crm.gestiontickets.dto.agente;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResumenAgente {
    
    private Integer idAgente;
    private String nombre;
    private String usuario;
    private Integer idRol;
    private String rol;
    private Integer idDepartamento;
    private String departamento;
    private List<PermisoRol> listaPermisos;
}
