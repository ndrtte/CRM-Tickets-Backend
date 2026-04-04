package com.crm.gestiontickets.ticket.dto.builder;

import java.time.LocalDateTime;
import java.util.List;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.cliente.entity.Cliente;
import com.crm.gestiontickets.ticket.dto.EtapaTicket;
import com.crm.gestiontickets.ticket.dto.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.EstadoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;

public class TicketEtapaAgenteDetalleBuilder {

    private TicketEtapaAgenteDetalle detalle;

    public TicketEtapaAgenteDetalleBuilder() {
        detalle = new TicketEtapaAgenteDetalle();
    }

    public TicketEtapaAgenteDetalleBuilder conCliente(Cliente cliente) {
        if (cliente != null) {
            detalle.setIdCliente(cliente.getIdCliente());
            detalle.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
        }
        return this;
    }

    public TicketEtapaAgenteDetalleBuilder conCategoria(Categoria categoria) {
        if (categoria != null) {
            detalle.setIdCategoria(categoria.getIdCategoria());
            detalle.setCategoria(categoria.getNombreCategoria());
        }
        return this;
    }

    public TicketEtapaAgenteDetalleBuilder conAgente(Agente agente) {
        detalle.setIdAgente(agente != null ? agente.getIdAgente() : null);
        detalle.setNombreAgente(agente != null ? agente.getNombre() + " " + agente.getApellido() : "Sin asignar");
        return this;
    }

    public TicketEtapaAgenteDetalleBuilder conDepartamento(PasoFlujo paso) {
        detalle.setIdDepartamento(paso != null && paso.getIdDepartamento() != null
                ? paso.getIdDepartamento().getIdDepartamento()
                : null);
        detalle.setDepartamento(paso != null && paso.getIdDepartamento() != null
                ? paso.getIdDepartamento().getNombreDepartamento()
                : "Sin asignar");
        return this;
    }

    public TicketEtapaAgenteDetalleBuilder conEstadoEtapa(String estadoEtapa) {
        detalle.setEstadoEtapa(estadoEtapa);
        return this;
    }

    public TicketEtapaAgenteDetalleBuilder conEstado(EstadoTicket estado) {
        if (estado != null) {
            detalle.setIdEstado(estado.getIdEstadoTicket());
            detalle.setEstadoTicket(estado.getEstadoTicket());
        }
        return this;
    }

    public TicketEtapaAgenteDetalleBuilder conIdTicket(String idTicket) {
        detalle.setIdTicket(idTicket);
        return this;
    }

    public TicketEtapaAgenteDetalleBuilder conListaEtapas(List<EtapaTicket> etapas) {
        detalle.setListaEtapas(etapas);
        return this;
    }

    public TicketEtapaAgenteDetalleBuilder conFechaCreacion(LocalDateTime fecha) {
        detalle.setFechaCreacion(fecha);
        return this;
    }

    public TicketEtapaAgenteDetalle build() {
        return detalle;
    }
}
