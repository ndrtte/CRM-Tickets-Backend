package com.crm.gestiontickets.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetalle {
    private String idTicket;
    private Long idCliente;
    private String nombreCliente;
    private Integer idCategoria;
    private String categoria;
    private Integer idAgente;
    private String nombreAgente;
    private Integer idEstado;
    private String estado;
    private LocalDateTime fechaCreacion;
    private Integer idDepartamento;
    private String departamento;
    private List<EtapaTicket> listaEtapas;
}
