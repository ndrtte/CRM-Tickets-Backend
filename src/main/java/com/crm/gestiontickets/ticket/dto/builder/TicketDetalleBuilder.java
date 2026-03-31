package com.crm.gestiontickets.ticket.dto.builder;

import java.util.List;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.cliente.entity.Cliente;
import com.crm.gestiontickets.ticket.dto.EtapaTicket;
import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.EstadoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;

public class TicketDetalleBuilder {

    private TicketDetalle ticketDetalle;

    public TicketDetalleBuilder() {
        ticketDetalle = new TicketDetalle();
    }

    public TicketDetalleBuilder conTicket(Ticket ticket) {
        if (ticket != null) {
            ticketDetalle.setIdTicket(ticket.getIdTicket());
            ticketDetalle.setFechaCreacion(ticket.getFechaCreacion());
        }
        return this;
    }

    public TicketDetalleBuilder conCliente(Cliente cliente) {
        if (cliente != null) {
            ticketDetalle.setIdCliente(cliente.getIdCliente());
            ticketDetalle.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
        }
        return this;
    }

    public TicketDetalleBuilder conCategoria(Categoria categoria) {
        if (categoria != null) {
            ticketDetalle.setIdCategoria(categoria.getIdCategoria());
            ticketDetalle.setCategoria(categoria.getNombreCategoria());
        }
        return this;
    }

    public TicketDetalleBuilder conAgente(Agente agente) {
        ticketDetalle.setIdAgente(agente != null ? agente.getIdAgente() : null);
        ticketDetalle.setNombreAgente(agente != null ? agente.getNombre() + " " + agente.getApellido() : "Sin asignar");
        return this;
    }

    public TicketDetalleBuilder conDepartamento(PasoFlujo paso) {
        ticketDetalle.setIdDepartamento(paso != null && paso.getIdDepartamento() != null
                ? paso.getIdDepartamento().getIdDepartamento()
                : null);
        ticketDetalle.setDepartamento(paso != null && paso.getIdDepartamento() != null
                ? paso.getIdDepartamento().getNombreDepartamento()
                : "Sin asignar");
        return this;
    }

    public TicketDetalleBuilder conEstado(EstadoTicket estado) {
        if (estado != null) {
            ticketDetalle.setIdEstado(estado.getIdEstadoTicket());
            ticketDetalle.setEstadoTicket(estado.getEstadoTicket());
        }
        return this;
    }

    public TicketDetalleBuilder conListaEtapas(List<EtapaTicket> etapas) {
        ticketDetalle.setListaEtapas(etapas);
        return this;
    }

    public TicketDetalle build() {
        return ticketDetalle;
    }
}
