package com.crm.gestiontickets.ticket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.entity.Departamento;
import com.crm.gestiontickets.agente.repository.DepartamentoRepository;
import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.IdPasoFlujo;
import com.crm.gestiontickets.ticket.dto.PasoFlujoDetalle;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.interfaces.IPasoFlujoService;
import com.crm.gestiontickets.ticket.repository.PasoFlujoRepository;
import com.crm.gestiontickets.ticket.repository.TicketRepository;

@Service
public class PasoFlujoService implements IPasoFlujoService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PasoFlujoRepository pasoFlujoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Override
    public Respuesta<IdPasoFlujo> obtenerPasoActual(String idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).get();

        Integer idPasoFlujo = ticket.getPasoActual() != null ? ticket.getPasoActual().getIdPasosFlujo() : null;

        if (idPasoFlujo == null) {
            return new Respuesta<>(false, "El ticket no tiene un paso de flujo asignado.", null);
        }

        return new Respuesta<>(true, "Paso de flujo actual recuperado correctamente.", new IdPasoFlujo(idPasoFlujo));
    }

    @Override
    public List<PasoFlujo> editarPasos(List<PasoFlujoDetalle> pasosDTO) {
        List<PasoFlujo> pasos = new ArrayList<>();
        for (PasoFlujoDetalle pasoDTO : pasosDTO) {
            PasoFlujo pasoFlujo = pasoFlujoRepository.findById(pasoDTO.getIdPaso()).get();
            pasoFlujo.setOrden(pasoDTO.getOrden());
            pasoFlujo.setDescripcion(pasoDTO.getDescripcion());
            Departamento departamento = departamentoRepository.findById(pasoDTO.getIdDepartamento()).get();
            pasoFlujo.setIdDepartamento(departamento);
        }

        pasoFlujoRepository.saveAll(pasos);

        return pasos;
    }

}
