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

    @Autowired
    private EstadoEtapaService estadoEtapaService;

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
        boolean pasoValido = etapas.stream().anyMatch(e -> e.getIdPaso().equals(idPaso));

        if (!pasoValido) {
            return new Respuesta<>(false, "El paso no pertenece al flujo del ticket", null);
        }

        PasoFlujo paso = pasoFlujoRepository.findById(idPaso).get();

        EstadoEtapaTicket estado = estadoEtapaService.obtenerEstado(paso, pasoActual, ticketCerrado);

        HistoricoTicket historico = historicoRepository.findTopByTicketAndPasoOrigenOrderByIdHistoricoTicketsDesc(ticket, paso);

        String nota = historico != null ? notaService.obtenerNotaHistorico(historico) : null;

        TicketEtapaDetalle detalle = ticketMapper.mapearATicketEtapaDetalle(ticket, paso, estado, nota, etapas);

        return new Respuesta<>(true, "Ok", detalle);
    }

}
