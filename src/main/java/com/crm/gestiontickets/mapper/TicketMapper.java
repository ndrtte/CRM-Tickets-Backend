package com.crm.gestiontickets.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.dto.ticket.EtapaTicket;
import com.crm.gestiontickets.dto.ticket.TicketDetalle;
import com.crm.gestiontickets.dto.ticket.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.dto.ticket.TicketEtapaDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.enums.EstadoEtapaTicketEnum;
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

    public TicketEtapaAgenteDetalle mapearTicketAEtapaDetalle(Ticket ticket, HistoricoTicket historico, FiltroTicketsAgenteEnum filtro) {
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

        if (historico != null) {
            paso = historico.getPasoDestino();
            detalle.setEstadoEtapa("Finalizado");
        } else {
            paso = ticket.getPasoActual();

            if (ticket.getEstado() != null && "Cerrado".equalsIgnoreCase(ticket.getEstado().getEstadoTicket())) {
                detalle.setEstadoEtapa("Finalizado");
            } else {
                detalle.setEstadoEtapa("En proceso");
            }
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

    public TicketEtapaDetalle mapearATicketEtapaDetalle(Ticket ticket, PasoFlujo paso, EstadoEtapaTicketEnum estado, String nota, List<EtapaTicket> etapas, HistoricoTicket historico) {
        TicketEtapaDetalle detalle = new TicketEtapaDetalle();

        Integer idDepartamento = paso.getIdDepartamento() != null ? paso.getIdDepartamento().getIdDepartamento() : null;
        String departamento = idDepartamento != null ? paso.getIdDepartamento().getNombreDepartamento() : "Sin departamento";

        Agente agente = historico != null ? historico.getAgenteOrigen() : ticket.getAgenteAsignado();
        Integer idAgente = agente != null ? agente.getIdAgente() : null;
        String nombreAgente = agente != null ? agente.getNombre() + " " + agente.getApellido() : "Sin asignar";

        Integer idCategoria = ticket.getCategoria() != null ? ticket.getCategoria().getIdCategoria() : null;
        String categoria = idCategoria != null ? ticket.getCategoria().getNombreCategoria() : null;

        Long idCliente = ticket.getCliente().getIdCliente();
        String nombreCliente = ticket.getCliente().getNombre();

        detalle.setIdTicket(ticket.getIdTicket());

        detalle.setIdCategoria(idCategoria);
        detalle.setCategoria(categoria);

        detalle.setIdCliente(idCliente);
        detalle.setNombreCliente(nombreCliente);

        detalle.setIdDepartamento(idDepartamento);
        detalle.setDepartamento(departamento);

        detalle.setIdAgente(idAgente);
        detalle.setNombreAgente(nombreAgente);

        detalle.setListaEtapas(etapas);
        detalle.setNota(nota);
        detalle.setPasoActual(paso.getDescripcion());
        detalle.setEstadoEtapa(estado);

        return detalle;
    }

}
