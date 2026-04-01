package com.crm.gestiontickets.ticket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* 
 * Patron comportamental: Strategy (solo define el tipo de filtro)
 */
@AllArgsConstructor
@Getter
public enum FiltroTicketsAgenteEnum {

    EN_PROCESO("En Proceso"),
    FINALIZADOS("Finalizado"),
    TODOS("Todos");

    private final String descripcion;

    
}

