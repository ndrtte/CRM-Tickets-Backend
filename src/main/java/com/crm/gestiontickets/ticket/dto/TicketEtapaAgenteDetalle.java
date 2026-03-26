package com.crm.gestiontickets.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketEtapaAgenteDetalle extends TicketDetalle{
    
    private String estadoEtapa;
}
