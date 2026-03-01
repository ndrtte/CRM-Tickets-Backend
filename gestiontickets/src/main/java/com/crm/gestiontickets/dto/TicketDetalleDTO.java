package com.crm.gestiontickets.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetalleDTO {
    private String idTicket;
    private Long idCliente;
    private String nombreCliente;
    private Integer idCategoria;
    private String categoria;
    private Integer idPasoActual;
    private String PasoActual;
    private Integer idAgente;
    private String nombreAgente;
    private Integer idEstado;
    private String estado;
    private LocalDateTime fechaCreacion;
}
