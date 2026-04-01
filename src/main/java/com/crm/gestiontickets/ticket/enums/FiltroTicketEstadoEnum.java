package com.crm.gestiontickets.ticket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FiltroTicketEstadoEnum {
    Nuevo("Nuevo"),
    Proceso("En Proceso"),
    Cerrado("Cerrado");

    private final String estado;
}
