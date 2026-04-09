package com.crm.gestiontickets.ticket.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;

public interface IFechaUtils {
    public LocalDateTime[] calcularRangoFecha(LocalDate fecha, FiltroFechaTicketEnum fechaOp);
}
