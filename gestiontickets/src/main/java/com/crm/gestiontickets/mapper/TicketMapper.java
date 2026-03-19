package com.crm.gestiontickets.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.dto.ticket.EtapaTicket;
import com.crm.gestiontickets.dto.ticket.TicketDetalle;
import com.crm.gestiontickets.dto.ticket.TicketEtapaDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.enums.EstadoEtapaTicketEnum;

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
            detalle.setEstado(estado.getEstadoTicket());
        }

        detalle.setListaEtapas(pasoFlujoMapper.mapearEtapas(categoria, pasoActual));

        return detalle;
    }

    public TicketEtapaDetalle mapearATicketEtapaDetalle(Ticket ticket,PasoFlujo paso,EstadoEtapaTicketEnum estado,String nota,List<EtapaTicket> etapas) {
        TicketEtapaDetalle detalle = new TicketEtapaDetalle();

        Integer idDepartamento = paso.getIdDepartamento() != null ? paso.getIdDepartamento().getIdDepartamento() : null;
        String departamento = idDepartamento != null ? paso.getIdDepartamento().getNombreDepartamento() : "Sin departamento";

        Integer idAgente = ticket.getAgenteAsignado() != null? ticket.getAgenteAsignado().getIdAgente(): null;
        String nombreAgente = ticket.getAgenteAsignado() != null ? ticket.getAgenteAsignado().getNombre() + ticket.getAgenteAsignado().getApellido(): "Sin asignar";

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
