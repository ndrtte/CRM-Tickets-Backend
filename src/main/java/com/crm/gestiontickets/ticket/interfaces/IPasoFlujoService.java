package com.crm.gestiontickets.ticket.interfaces;

import java.util.List;

import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.IdPasoFlujo;
import com.crm.gestiontickets.ticket.dto.PasoFlujoDetalle;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;

public interface IPasoFlujoService {

    Respuesta<IdPasoFlujo> obtenerPasoActual(String idTicket);

    List<PasoFlujo> editarPasos(List<PasoFlujoDetalle> pasosDTO);
}
