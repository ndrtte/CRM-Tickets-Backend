package com.crm.gestiontickets.enums;

import lombok.Getter;

@Getter
public enum EstadoEtapaTicketEnum {

    NO_INICIADO("No iniciado"),
    EN_PROCESO("En proceso"),
    FINALIZADO("Finalizado");

    private final String descripcion;

    EstadoEtapaTicketEnum(String descripcion) {
        this.descripcion = descripcion;
    }

}