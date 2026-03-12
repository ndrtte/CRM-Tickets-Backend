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
public class TicketEtapaDetalle extends BaseTicketDetalle{

    private String pasoActual;
    private String nota;
    private EstadoEtapaTicket estadoEtapa;
}
