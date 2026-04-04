/*Patron, comportamental: strategy, filtra los ticket de un agente */

package com.crm.gestiontickets.ticket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FiltroTicketsAgentesEnum {
    EN_PROCESO("En Proceso"),
    FINALIZADOS("Finalizado"),
    TODOS("Todos");

    private final String descripcion;

}
