package com.crm.gestiontickets.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TicketApertura {
    //Este dto ahora mismos sera solo asi
    //Posiblemente la ocupare para otros atributos
    //Para que sea mas escalable
    private Integer idAgenteAsignado;
    private Long idCliente;

}
