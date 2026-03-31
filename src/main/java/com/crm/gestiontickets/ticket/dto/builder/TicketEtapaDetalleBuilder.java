package com.crm.gestiontickets.ticket.dto.builder;

import java.util.List;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.cliente.entity.Cliente;
import com.crm.gestiontickets.ticket.dto.EtapaTicket;
import com.crm.gestiontickets.ticket.dto.TicketEtapaDetalle;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.enums.EstadoEtapaTicketEnum;

public class TicketEtapaDetalleBuilder {

    private TicketEtapaDetalle detalle;

    public TicketEtapaDetalleBuilder() {
        detalle = new TicketEtapaDetalle();
    }

    public TicketEtapaDetalleBuilder conCliente(Cliente cliente) {
        if (cliente != null) {
            detalle.setIdCliente(cliente.getIdCliente());
            detalle.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
        }
        return this;
    }

    public TicketEtapaDetalleBuilder conCategoria(Categoria categoria) {
        if (categoria != null) {
            detalle.setIdCategoria(categoria.getIdCategoria());
            detalle.setCategoria(categoria.getNombreCategoria());
        }
        return this;
    }

    public TicketEtapaDetalleBuilder conAgente(Agente agente) {
        detalle.setIdAgente(agente != null ? agente.getIdAgente() : null);
        detalle.setNombreAgente(agente != null ? agente.getNombre() + " " + agente.getApellido() : "Sin asignar");
        return this;
    }

    public TicketEtapaDetalleBuilder conDepartamento(PasoFlujo paso) {
        detalle.setIdDepartamento(paso != null && paso.getIdDepartamento() != null
                ? paso.getIdDepartamento().getIdDepartamento()
                : null);
        detalle.setDepartamento(paso != null && paso.getIdDepartamento() != null
                ? paso.getIdDepartamento().getNombreDepartamento()
                : "Sin asignar");
        return this;
    }

    public TicketEtapaDetalleBuilder conPasoActual(String pasoActual) {
        detalle.setPasoActual(pasoActual);
        return this;
    }

    public TicketEtapaDetalleBuilder conEstadoEtapa(EstadoEtapaTicketEnum estado) {
        detalle.setEstadoEtapa(estado);
        return this;
    }

    public TicketEtapaDetalleBuilder conNota(String nota) {
        detalle.setNota(nota);
        return this;
    }

    public TicketEtapaDetalleBuilder conIdTicket(String idTicket) {
        detalle.setIdTicket(idTicket);
        return this;
    }

    public TicketEtapaDetalleBuilder conListaEtapas(List<EtapaTicket> etapas) {
        detalle.setListaEtapas(etapas);
        return this;
    }

    public TicketEtapaDetalle build() {
        return detalle;
    }
}
