package com.crm.gestiontickets.service.ticket;

import org.springframework.stereotype.Component;

import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.enums.EstadoEtapaTicketEnum;

@Component
public class EstadoEtapaService {

    public EstadoEtapaTicketEnum obtenerEstado(PasoFlujo paso, PasoFlujo pasoActual, boolean ticketCerrado) {
        if (paso.getDescripcion().equals("APERTURA")) {

            if (pasoActual != null && pasoActual.getOrden() > 0) {
                return EstadoEtapaTicketEnum.FINALIZADO;
            } else {
                return EstadoEtapaTicketEnum.EN_PROCESO;
            }
        }

        if (pasoActual == null) {
            return EstadoEtapaTicketEnum.NO_INICIADO;
        }

        int ordenActual = pasoActual.getOrden();
        int ordenPaso = paso.getOrden();

        if (ordenPaso < ordenActual) {
            return EstadoEtapaTicketEnum.FINALIZADO;

        } else if (ordenPaso == ordenActual) {
            return ticketCerrado
                    ? EstadoEtapaTicketEnum.FINALIZADO
                    : EstadoEtapaTicketEnum.EN_PROCESO;

        } else {
            return EstadoEtapaTicketEnum.NO_INICIADO;
        }
    }

}
