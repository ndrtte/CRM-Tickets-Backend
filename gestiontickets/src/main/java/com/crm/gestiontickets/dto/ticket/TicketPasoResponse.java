package com.crm.gestiontickets.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TicketPasoResponse {
    private String idTicket;
    private Integer idPaso;

}
