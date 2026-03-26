package com.crm.gestiontickets.ticket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FiltroTicketsAgenteEnum {
    EN_PROCESO("En Proceso"),
    FINALIZADOS("Finalizado"),
    TODOS("Todos");

    private final String descripcion;

}
