package com.crm.gestiontickets.agente.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepartamentoDetalle {

    private Integer idDepartamento;
    private String nombreDepartamento;
    private String descripcion;
    private LocalDateTime fechaCreacion;         
    private LocalDateTime fechaActualizacion;
    private String activo;

}