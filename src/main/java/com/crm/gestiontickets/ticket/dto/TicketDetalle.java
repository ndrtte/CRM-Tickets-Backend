/*patron: creacional: abstract factory,  hereda los dtoas de BaseTicketDetalle */
package com.crm.gestiontickets.ticket.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetalle extends BaseTicketDetalle {
    private Integer idEstado;
    private String estadoTicket;
    private LocalDateTime fechaCreacion;
}
