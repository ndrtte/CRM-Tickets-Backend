/* Patrón: creacional: DTO, representa las etapas del ticket */

package com.crm.gestiontickets.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EtapaTicket {
    private Integer idPaso;
    private String descripcion;
    private boolean esActual;
}
