package com.crm.gestiontickets.enums;

import lombok.Getter;

@Getter
public enum EstadoEtapaTicket {

    NO_INICIADO("No Iniciado"),
    EN_PROCESO("En Proceso"),
    FINALIZADO("Finalizado");

    private final String descripcion;

    EstadoEtapaTicket(String descripcion) {
        this.descripcion = descripcion;
    }

}