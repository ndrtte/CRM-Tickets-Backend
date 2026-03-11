package com.crm.gestiontickets.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.dto.EtapaTicket;
import com.crm.gestiontickets.dto.TicketDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.Flujo;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.repository.FlujoRepository;

@Component
public class TicketMapper {

    @Autowired
    private FlujoRepository flujoRepository;

    public TicketDetalle mapearTicketADetalle(Ticket ticket) {
        TicketDetalle detalle = new TicketDetalle();

        detalle.setIdTicket(ticket.getIdTicket());
        detalle.setFechaCreacion(ticket.getFechaCreacion());

        Cliente cliente = ticket.getCliente();
        if (cliente != null) {
            detalle.setIdCliente(cliente.getIdCliente());
            detalle.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
        }

        Categoria categoria = ticket.getCategoria();
        if (categoria != null) {
            detalle.setIdCategoria(categoria.getIdCategoria());
            detalle.setCategoria(categoria.getNombre());
        }

        PasoFlujo pasoActual = ticket.getPasoActual();
        if (pasoActual != null) {
            detalle.setIdDepartamento(pasoActual.getIdDepartamento() != null
                    ? pasoActual.getIdDepartamento().getIdDepartamento()
                    : null);
            detalle.setDepartamento(pasoActual.getIdDepartamento() != null
                    ? pasoActual.getIdDepartamento().getNombreDepartamento()
                    : "Sin asignar");
        }

        Agente agente = ticket.getAgenteAsignado();
        if (agente != null) {
            detalle.setIdAgente(agente.getIdAgente());
            detalle.setNombreAgente(agente.getNombre() + " " + agente.getApellido());
        }

        EstadoTicket estado = ticket.getEstado();
        if (estado != null) {
            detalle.setIdEstado(estado.getIdEstadoTicket());
            detalle.setEstado(estado.getEstadoTicket());
        }

        List<EtapaTicket> listaEtapas = new ArrayList<>();

        detalle.setListaEtapas(listaEtapas);
        if (categoria != null) {
            Flujo flujo = flujoRepository.findByCategoria(categoria);
            if (flujo != null && flujo.getPasos() != null) {
                for (PasoFlujo paso : flujo.getPasos()) {
                    EtapaTicket etapa = new EtapaTicket();
                    etapa.setIdPaso(paso.getIdPasosFlujo());
                    etapa.setDescripcion(paso.getDescripcion());
                    etapa.setEsActual(pasoActual != null
                            && paso.getIdPasosFlujo().equals(pasoActual.getIdPasosFlujo()));
                    detalle.getListaEtapas().add(etapa);
                }
            }
        }

        return detalle;
    }

}
