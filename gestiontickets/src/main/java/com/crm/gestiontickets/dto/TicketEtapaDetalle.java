package com.crm.gestiontickets.dto;

import com.crm.gestiontickets.enums.EstadoEtapaTicket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketEtapaDetalle{

    private String idTicket;
    private String nombreCliente;
    private String categoria;
    private String pasoActual;
    private String agente;
    private String departamento;
    private String nota;
    private EstadoEtapaTicket estadoTicket;
}
