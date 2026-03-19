package com.crm.gestiontickets.service.ticket;

import org.springframework.stereotype.Component;

import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.enums.EstadoEtapaTicket;

@Component
public class EstadoEtapaService {

    public EstadoEtapaTicket obtenerEstado(PasoFlujo paso, PasoFlujo pasoActual, boolean ticketCerrado) {
        if (paso.getDescripcion().equals("APERTURA")) {

            if (pasoActual != null && pasoActual.getOrden() > 0) {
                return EstadoEtapaTicket.FINALIZADO;
            } else {
                return EstadoEtapaTicket.EN_PROCESO;
            }
        }

        if (pasoActual == null) {
            return EstadoEtapaTicket.NO_INICIADO;
        }

        int ordenActual = pasoActual.getOrden();
        int ordenPaso = paso.getOrden();

        if (ordenPaso < ordenActual) {
            return EstadoEtapaTicket.FINALIZADO;

        } else if (ordenPaso == ordenActual) {
            return ticketCerrado
                    ? EstadoEtapaTicket.FINALIZADO
                    : EstadoEtapaTicket.EN_PROCESO;

        } else {
            return EstadoEtapaTicket.NO_INICIADO;
        }
    }

}
