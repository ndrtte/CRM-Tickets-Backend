package com.crm.gestiontickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreacionDTO {
    
    private String idTicket;

    private Integer idAgente;

    private Integer idCategoria;

}
