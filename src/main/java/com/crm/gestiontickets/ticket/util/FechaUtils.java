package com.crm.gestiontickets.ticket.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;

@Component
public class FechaUtils {

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
