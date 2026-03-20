package com.crm.gestiontickets.service.ticket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.ticket.EtapaTicket;
import com.crm.gestiontickets.dto.ticket.TicketDetalle;
import com.crm.gestiontickets.dto.ticket.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.dto.ticket.TicketEtapaDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.enums.EstadoEtapaTicketEnum;
import com.crm.gestiontickets.enums.FiltroTicketsAgenteEnum;
import com.crm.gestiontickets.mapper.PasoFlujoMapper;
import com.crm.gestiontickets.mapper.TicketMapper;
import com.crm.gestiontickets.repository.AgenteRepository;
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
    private AgenteRepository agenteRepository;

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

        List<Ticket> listaTickets = ticketRepository.findTicketsByDepartamento(idDepartamento);

        for (Ticket ticket : listaTickets) {
            TicketDetalle ticketDetalle = ticketMapper.mapearTicketADetalle(ticket);
            listaTicketsDTO.add(ticketDetalle);
        }

        return listaTicketsDTO;
    }

    public List<TicketEtapaAgenteDetalle> obtenerTicketsAgente(
            Integer idAgente,
            FiltroTicketsAgenteEnum filtro) {

        Agente agente = agenteRepository.findById(idAgente).orElseThrow();

        return switch (filtro) {
            case EN_PROCESO ->
                mapearEnProceso(agente);
            case FINALIZADOS ->
                mapearFinalizados(agente);
            case TODOS ->
                mapearTodos(agente);
        };
    }

    private List<TicketEtapaAgenteDetalle> mapearEnProceso(Agente agente) {

        List<Ticket> tickets = ticketRepository.findByAgenteAsignado(agente);
        List<TicketEtapaAgenteDetalle> response = new ArrayList<>();

        for (Ticket t : tickets) {
            response.add(ticketMapper.mapearTicketAEtapaDetalle(t, null, FiltroTicketsAgenteEnum.EN_PROCESO));
        }

        return response;
    }

    private List<TicketEtapaAgenteDetalle> mapearFinalizados(Agente agente) {

        List<HistoricoTicket> historicos
                = historicoRepository.findHistoricoTicketByAgenteOrigen(agente);

        List<TicketEtapaAgenteDetalle> response = new ArrayList<>();

        for (HistoricoTicket h : historicos) {
            response.add(
                    ticketMapper.mapearTicketAEtapaDetalle(
                            h.getTicket(),
                            h,
                            FiltroTicketsAgenteEnum.FINALIZADOS
                    )
            );
        }

        return response;
    }

    private List<TicketEtapaAgenteDetalle> mapearTodos(Agente agente) {

        List<Ticket> enProceso = ticketRepository.findByAgenteAsignado(agente);
        List<HistoricoTicket> historicos = historicoRepository.findHistoricoTicketByAgenteOrigen(agente);

        List<TicketEtapaAgenteDetalle> response = new ArrayList<>();

        for (Ticket t : enProceso) {
            response.add(ticketMapper.mapearTicketAEtapaDetalle(t, null, FiltroTicketsAgenteEnum.EN_PROCESO));
        }

        List<String> idsEnProceso = new ArrayList<>();
        for (Ticket t : enProceso) {
            idsEnProceso.add(t.getIdTicket());
        }

        for (HistoricoTicket h : historicos) {
            if (!idsEnProceso.contains(h.getTicket().getIdTicket())) {
                response.add(ticketMapper.mapearTicketAEtapaDetalle(h.getTicket(), h, FiltroTicketsAgenteEnum.FINALIZADOS));
            }
        }

        return response;
    }

    public Respuesta<TicketEtapaDetalle> obtenerEstadoTicketEtapa(String idTicket, Integer idPaso) {

        Ticket ticket = ticketRepository.findById(idTicket).get();

        PasoFlujo pasoActual = ticket.getPasoActual();

        boolean ticketCerrado = ticket.getEstado().getEstadoTicket().equals("Cerrado");

        List<EtapaTicket> etapas = pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), pasoActual);
        boolean pasoValido = etapas.stream().anyMatch(e -> e.getIdPaso().equals(idPaso));

        if (!pasoValido) {
            return new Respuesta<>(false, "El paso no pertenece al flujo del ticket", null);
        }

        PasoFlujo paso = pasoFlujoRepository.findById(idPaso).get();

        EstadoEtapaTicketEnum estado = estadoEtapaService.obtenerEstado(ticket, paso, pasoActual, ticketCerrado);

        HistoricoTicket historico = historicoRepository
                .findTopByTicketAndPasoOrigenOrderByIdHistoricoTicketsDesc(ticket, paso);

        String nota = historico != null ? notaService.obtenerNotaHistorico(historico) : null;

        TicketEtapaDetalle detalle = ticketMapper.mapearATicketEtapaDetalle(ticket, paso, estado, nota, etapas, historico);
        return new Respuesta<>(true, "Ok", detalle);
    }

}