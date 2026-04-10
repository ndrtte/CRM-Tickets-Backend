/*Patron: estructural: facade, permite obtener el paso acutla de un ticket */
package com.crm.gestiontickets.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.IdPasoFlujo;
import com.crm.gestiontickets.ticket.dto.PasoFlujoDetalle;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.repository.PasoFlujoRepository;
import com.crm.gestiontickets.ticket.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Service
public class PasoFlujoService {

    @Autowired
    private TicketRepository ticketRepository;

    public Respuesta<IdPasoFlujo> obtenerPasoActual(String idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).get();

        Integer idPasoFlujo = ticket.getPasoActual() != null ? ticket.getPasoActual().getIdPasosFlujo() : null;

        if (idPasoFlujo == null) {
            return new Respuesta<>(false, "El ticket no tiene un paso de flujo asignado.", null);
        }

        return new Respuesta<>(true, "Paso de flujo actual recuperado correctamente.", new IdPasoFlujo(idPasoFlujo));
    }

    //habilitar o desabilitar una etapa del flujo
 @Autowired
private PasoFlujoRepository pasoFlujoRepository;

@Transactional
public Respuesta<PasoFlujoDetalle> cambiarEstado(Integer idPaso) {

    PasoFlujo paso = pasoFlujoRepository.findById(idPaso)
            .orElseThrow(() -> new RuntimeException("Paso no encontrado"));

    // toggle estado
    paso.setActivo("S".equals(paso.getActivo()) ? "N" : "S");

    pasoFlujoRepository.save(paso);

    PasoFlujoDetalle dto = new PasoFlujoDetalle();
    dto.setIdPaso(paso.getIdPasosFlujo());
    dto.setOrden(paso.getOrden());
    dto.setDescripcion(paso.getDescripcion());
    dto.setIdDepartamento(paso.getIdDepartamento().getIdDepartamento());
    dto.setNombreDepartamento(paso.getIdDepartamento().getNombreCategoria()); // revisa entidad
    dto.setActivo(paso.getActivo());

    return new Respuesta<>(true, "OK", dto);
}

}
