package com.crm.gestiontickets.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketEstadoEtapaDetalle extends BaseTicketDetalle{
    
    private Integer idEstadoEtapa;
    private String estadoEtapa;
}
