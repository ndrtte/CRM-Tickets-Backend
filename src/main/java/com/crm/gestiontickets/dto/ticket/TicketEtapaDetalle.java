package com.crm.gestiontickets.dto.ticket;

import com.crm.gestiontickets.enums.EstadoEtapaTicketEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketEtapaDetalle extends BaseTicketDetalle{

    private String pasoActual;
    private String nota;
    private EstadoEtapaTicketEnum estadoEtapa;
}
