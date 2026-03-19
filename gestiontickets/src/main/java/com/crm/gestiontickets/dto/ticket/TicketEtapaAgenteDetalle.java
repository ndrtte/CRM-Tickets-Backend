package com.crm.gestiontickets.dto.ticket;

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
