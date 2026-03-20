package com.crm.gestiontickets.service.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.enums.EstadoEtapaTicketEnum;
import com.crm.gestiontickets.repository.HistoricoTicketRepository;

@Component
public class EstadoEtapaService {

    @Autowired
    private HistoricoTicketRepository historicoRepository;

    public EstadoEtapaTicketEnum obtenerEstado(Ticket ticket, PasoFlujo paso, PasoFlujo pasoActual, boolean ticketCerrado) {

        if (paso.getDescripcion().equals("APERTURA")) {

            boolean aperturaFinalizada = historicoRepository.existsByTicketAndPasoOrigen(ticket, paso);

            return aperturaFinalizada ? EstadoEtapaTicketEnum.FINALIZADO : EstadoEtapaTicketEnum.EN_PROCESO;
        }

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
