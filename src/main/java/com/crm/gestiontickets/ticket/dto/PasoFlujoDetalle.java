package com.crm.gestiontickets.ticket.dto;

import com.crm.gestiontickets.ticket.entity.Flujo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasoFlujoDetalle {

    private Integer idPaso;
    private Integer orden;
    private String descripcion;
    private Integer idDepartamento;
    private String nombreDepartamento;

}