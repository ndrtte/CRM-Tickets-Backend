package com.crm.gestiontickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreacion {
    
    private String idTicket;

    private Integer idCategoria;

    private String nota;

}
