package com.crm.gestiontickets.service.ticket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.ticket.EtapaTicket;
import com.crm.gestiontickets.dto.ticket.TicketDetalle;
import com.crm.gestiontickets.dto.ticket.TicketEtapaDetalle;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.enums.EstadoEtapaTicket;
import com.crm.gestiontickets.mapper.PasoFlujoMapper;
import com.crm.gestiontickets.mapper.TicketMapper;
import com.crm.gestiontickets.repository.ClienteRepository;
import com.crm.gestiontickets.repository.HistoricoTicketRepository;
import com.crm.gestiontickets.repository.PasoFlujoRepository;
import com.crm.gestiontickets.repository.TicketRepository;

@Service
public class TicketBusquedaService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private PasoFlujoMapper pasoFlujoMapper;

    @Autowired
    private NotaService notaService;

    @Autowired
    private HistoricoTicketRepository historicoRepository;

    @Autowired
    private PasoFlujoRepository pasoFlujoRepository;

    public TicketDetalle obtenerTicketDTO(String idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).get();
        return ticketMapper.mapearTicketADetalle(ticket);
    }

    public List<TicketDetalle> obtenerTicketsCliente(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).get();

        List<Ticket> listaTickets = ticketRepository.findByCliente(cliente);
        List<TicketDetalle> listaTicketsDTO = new ArrayList<>();

        for (Ticket ticket : listaTickets) {
            TicketDetalle detalle = ticketMapper.mapearTicketADetalle(ticket);
            listaTicketsDTO.add(detalle);
        }

        return listaTicketsDTO;
    }

    public List<TicketDetalle> obtenerTicketsDepartamento(Integer idDepartamento) {

        List<TicketDetalle> listaTicketsDTO = new ArrayList<>();

        List<Ticket> listaTicket = ticketRepository.findTicketsByDepartamento(idDepartamento);

        for (Ticket ticket : listaTicket) {
            TicketDetalle ticketDetalle = ticketMapper.mapearTicketADetalle(ticket);
            listaTicketsDTO.add(ticketDetalle);
        }

        return listaTicketsDTO;
    }

    public Respuesta<TicketEtapaDetalle> obtenerEtapaTicket(String idTicket, Integer idPaso) {
        Ticket ticket = ticketRepository.findById(idTicket).get();

        PasoFlujo pasoActual = ticket.getPasoActual();

        boolean ticketCerrado = ticket.getEstado().getEstadoTicket().equals("Cerrado");

        List<EtapaTicket> etapas = pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), pasoActual);

        PasoFlujo paso = pasoFlujoRepository.findById(idPaso).get();

        EstadoEtapaTicket estado = obtenerEstado(paso, pasoActual, ticketCerrado);

        TicketEtapaDetalle detalle = new TicketEtapaDetalle();

        HistoricoTicket historico = historicoRepository.findTopByTicketAndPasoOrigenOrderByIdHistoricoTicketsDesc(ticket, paso);

        String nota = historico!= null ? notaService.obtenerNotaHistorico(historico) : null;

        detalle.setNota(nota);

        detalle.setPasoActual(paso.getDescripcion());
        detalle.setEstadoEtapa(estado);

        detalle.setCategoria(ticket.getCategoria().getNombreCategoria());

        String departamento = paso.getIdDepartamento() != null ? paso.getIdDepartamento().getNombreDepartamento() : "Sin departamento";
        detalle.setDepartamento(departamento);

        detalle.setIdAgente(ticket.getAgenteAsignado() != null ? ticket.getAgenteAsignado().getIdAgente() : null);

        detalle.setIdCategoria(ticket.getCategoria().getIdCategoria());
        detalle.setIdCliente(ticket.getCliente().getIdCliente());

        Integer idDepartamento = paso.getIdDepartamento() != null ? paso.getIdDepartamento().getIdDepartamento() : null;
        detalle.setIdDepartamento(idDepartamento);

        detalle.setIdTicket(ticket.getIdTicket());
        detalle.setListaEtapas(etapas);

        detalle.setNombreAgente(ticket.getAgenteAsignado() != null ? ticket.getAgenteAsignado().getNombre() : "Sin asignar");

        detalle.setNombreCliente(ticket.getCliente().getNombre());

        return new Respuesta<>(true, "Ok", detalle);
    }

    private EstadoEtapaTicket obtenerEstado(PasoFlujo paso, PasoFlujo pasoActual, boolean ticketCerrado) {
        if (paso.getDescripcion().equals("APERTURA")) {

            if (pasoActual != null && pasoActual.getOrden() > 0) {
                return EstadoEtapaTicket.FINALIZADO;
            } else {
                return EstadoEtapaTicket.EN_PROCESO;
            }
        }

        if (pasoActual == null) {
            return EstadoEtapaTicket.NO_INICIADO;
        }

        int ordenActual = pasoActual.getOrden();
        int ordenPaso = paso.getOrden();

        if (ordenPaso < ordenActual) {
            return EstadoEtapaTicket.FINALIZADO;

        } else if (ordenPaso == ordenActual) {
            return ticketCerrado
                    ? EstadoEtapaTicket.FINALIZADO
                    : EstadoEtapaTicket.EN_PROCESO;

        } else {
            return EstadoEtapaTicket.NO_INICIADO;
        }
    }

}
