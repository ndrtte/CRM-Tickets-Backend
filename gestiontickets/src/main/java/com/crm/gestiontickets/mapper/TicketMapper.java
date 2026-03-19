package com.crm.gestiontickets.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.dto.ticket.TicketDetalle;
import com.crm.gestiontickets.dto.ticket.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.enums.FiltroTicketsAgenteEnum;

@Component
public class TicketMapper {

    @Autowired
    private PasoFlujoMapper pasoFlujoMapper;

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
            detalle.setCategoria(categoria.getNombreCategoria());
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
            detalle.setEstadoTicket(estado.getEstadoTicket());
        }

        detalle.setListaEtapas(pasoFlujoMapper.mapearEtapas(categoria, pasoActual));

        return detalle;
    }

    public TicketEtapaAgenteDetalle mapearTicketAEtapaDetalle( Ticket ticket, HistoricoTicket historico, FiltroTicketsAgenteEnum filtro) {
        TicketEtapaAgenteDetalle detalle = new TicketEtapaAgenteDetalle();

        detalle.setIdTicket(ticket.getIdTicket());

        Cliente cliente = ticket.getCliente();
        if (cliente != null) {
            detalle.setIdCliente(cliente.getIdCliente());
            detalle.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
        }

        Categoria categoria = ticket.getCategoria();
        if (categoria != null) {
            detalle.setIdCategoria(categoria.getIdCategoria());
            detalle.setCategoria(categoria.getNombreCategoria());
        }

        PasoFlujo paso;

        if (filtro == FiltroTicketsAgenteEnum.FINALIZADOS && historico != null) {

            paso = historico.getPasoDestino();
            detalle.setEstadoEtapa(filtro.getDescripcion());

        } else {

            paso = ticket.getPasoActual();
            detalle.setEstadoEtapa(filtro.getDescripcion());
        }

        if (paso != null && paso.getIdDepartamento() != null) {
            detalle.setIdDepartamento(paso.getIdDepartamento().getIdDepartamento());
            detalle.setDepartamento(paso.getIdDepartamento().getNombreDepartamento());
        } else {
            detalle.setDepartamento("Sin asignar");
        }

        Agente agente = ticket.getAgenteAsignado();
        if (agente != null) {
            detalle.setIdAgente(agente.getIdAgente());
            detalle.setNombreAgente(agente.getNombre() + " " + agente.getApellido());
        }
                
        EstadoTicket estado = ticket.getEstado();
        if (estado != null) {
            detalle.setIdEstado(estado.getIdEstadoTicket());
            detalle.setEstadoTicket(estado.getEstadoTicket());
        }

        detalle.setFechaCreacion(ticket.getFechaCreacion());
        detalle.setListaEtapas(pasoFlujoMapper.mapearEtapas(categoria, ticket.getPasoActual()));

        return detalle;
    }

}
