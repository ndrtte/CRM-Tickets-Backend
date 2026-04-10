package com.crm.gestiontickets.ticket.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlujoDetalle {

    private Integer idFlujo;
    private String descripcion;
    private Integer idCategoria;
    private String nombreCategoria;
    private String activo;


    
    private List<PasoFlujoDetalle> pasos;
}