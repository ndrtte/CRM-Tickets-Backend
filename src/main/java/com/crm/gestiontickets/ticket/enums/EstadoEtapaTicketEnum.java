/*patron: estructural: strategy, representa el estado del ticket en cada etapa */
package com.crm.gestiontickets.ticket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoEtapaTicketEnum {

    NO_INICIADO,
    EN_PROCESO,
    FINALIZADO

}