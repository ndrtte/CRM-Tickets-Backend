package com.crm.gestiontickets.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoEtapaTicketEnum {

    NO_INICIADO("No iniciado"),
    EN_PROCESO("En proceso"),
    FINALIZADO("Finalizado");

    private final String descripcion;

}