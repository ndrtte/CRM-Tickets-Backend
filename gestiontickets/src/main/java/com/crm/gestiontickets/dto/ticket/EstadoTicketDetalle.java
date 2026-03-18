package com.crm.gestiontickets.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EstadoTicketDetalle {
   
    private Integer idEstadoTicket;

    private String estado;
}
