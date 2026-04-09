package com.crm.gestiontickets.ticket.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;
import com.crm.gestiontickets.ticket.interfaces.IFechaUtils;

@Component
public class FechaUtils implements IFechaUtils {

    @Override
    public LocalDateTime[] calcularRangoFecha(LocalDate fecha, FiltroFechaTicketEnum fechaOp) {
        LocalDateTime fechaInicio = null;
        LocalDateTime fechaFin = null;

        if (fecha != null && fechaOp != null) {
            switch (fechaOp) {
                case IGUAL -> {
                    fechaInicio = fecha.atStartOfDay();
                    fechaFin = fecha.plusDays(1).atStartOfDay();
                }
                case MAYOR ->
                    fechaInicio = fecha.plusDays(1).atStartOfDay();
                case MENOR ->
                    fechaFin = fecha.atStartOfDay();
            }
        }
        return new LocalDateTime[]{fechaInicio, fechaFin};
    }
}
