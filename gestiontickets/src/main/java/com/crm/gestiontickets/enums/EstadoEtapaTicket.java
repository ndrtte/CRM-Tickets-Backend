package com.crm.gestiontickets.enums;

import lombok.Getter;

@Getter
public enum EstadoEtapaTicket {

    NO_INICIADO("No iniciado"),
    EN_PROCESO("En proceso"),
    FINALIZADO("Finalizado");

    private final String descripcion;

    EstadoEtapaTicket(String descripcion) {
        this.descripcion = descripcion;
    }

}