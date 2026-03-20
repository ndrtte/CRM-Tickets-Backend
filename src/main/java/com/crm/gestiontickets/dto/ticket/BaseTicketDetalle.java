package com.crm.gestiontickets.dto.ticket;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseTicketDetalle {
    
    private String idTicket;
    private Long idCliente;
    private String nombreCliente;
    private Integer idCategoria;
    private String categoria;
    private Integer idAgente;
    private String nombreAgente;
    private Integer idDepartamento;
    private String departamento;
    private List<EtapaTicket> listaEtapas;

}
