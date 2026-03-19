package com.crm.gestiontickets.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FiltroTicketsAgenteEnum {
    EN_PROCESO("En Proceso"),
    FINALIZADOS("Finalizado"),
    TODOS("Todos");

    private final String descripcion;

}
