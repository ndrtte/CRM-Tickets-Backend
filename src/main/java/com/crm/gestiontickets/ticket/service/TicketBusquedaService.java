/*Patron: estructural: facade, obtine la informacion de un ticket y sus etapas */
package com.crm.gestiontickets.ticket.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.agente.repository.AgenteRepository;
import com.crm.gestiontickets.cliente.entity.Cliente;
import com.crm.gestiontickets.cliente.repository.ClienteRepository;
import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.EtapaTicket;
import com.crm.gestiontickets.ticket.dto.TicketDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaAgenteDetalle;
import com.crm.gestiontickets.ticket.dto.TicketEtapaDetalle;
import com.crm.gestiontickets.ticket.dto.builder.TicketEtapaDetalleBuilder;
import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.enums.EstadoEtapaTicketEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketsAgenteEnum;
import com.crm.gestiontickets.ticket.mapper.PasoFlujoMapper;
import com.crm.gestiontickets.ticket.mapper.TicketMapper;
import com.crm.gestiontickets.ticket.repository.HistoricoTicketRepository;
import com.crm.gestiontickets.ticket.repository.PasoFlujoRepository;
import com.crm.gestiontickets.ticket.repository.TicketRepository;

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

        return listaTickets.stream()
                .map(ticketMapper::mapearTicketADetalle)
                .toList();
    }

    public List<TicketDetalle> obtenerTicketsDepartamento(Integer idDepartamento) {
        List<Ticket> listaTickets = ticketRepository.findTicketsByDepartamento(idDepartamento);

        return listaTickets.stream()
                .map(ticketMapper::mapearTicketADetalle)
                .toList();
    }

    public List<TicketEtapaAgenteDetalle> obtenerTicketsAgente(
            Integer idAgente,
            FiltroTicketsAgenteEnum filtro) {

        Agente agente = agenteRepository.findById(idAgente).orElseThrow();

        return switch (filtro) {
            case EN_PROCESO ->
                ticketMapper.mapearEnProceso(agente);
            case FINALIZADOS ->
                ticketMapper.mapearFinalizados(agente);
            case TODOS ->
                ticketMapper.mapearTodos(agente);
        };
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

        TicketEtapaDetalle detalle = new TicketEtapaDetalleBuilder()
                .conIdTicket(ticket.getIdTicket())
                .conCliente(ticket.getCliente())
                .conCategoria(ticket.getCategoria())
                .conDepartamento(paso)
                .conAgente(historico != null ? historico.getAgenteOrigen() : ticket.getAgenteAsignado())
                .conListaEtapas(etapas)
                .conNota(nota)
                .conPasoActual(paso.getDescripcion())
                .conEstadoEtapa(estado)
                .build();

        return new Respuesta<>(true, "Ok", detalle);
    }

    //filtrar busqueda de tickets por cliente, estado y fecha
    public List<TicketDetalle> obtenerTicketsClienteFiltro(
            Long idCliente,
            FiltroTicketsAgenteEnum estado,
            LocalDate fecha
    ) {

        return ticketRepository.findAll().stream()
                .filter(t -> t.getCliente().getIdCliente().equals(idCliente))
                .filter(t -> filtrarPorEstado(t, estado))
                .filter(t -> filtrarPorFecha(t.getFechaCreacion(), fecha))
                .map(ticketMapper::mapearTicketADetalle)
                .toList();
    }

    // ---------------------------
    // FILTROS (CORREGIDOS)
    // ---------------------------

    private boolean filtrarPorEstado(Ticket ticket, FiltroTicketsAgenteEnum estado) {

        if (estado == null || estado == FiltroTicketsAgenteEnum.TODOS) {
            return true;
        }

        String estadoTicket = ticket.getEstado().getEstadoTicket();

        return switch (estado) {
            case EN_PROCESO -> "En Proceso".equalsIgnoreCase(estadoTicket);
            case FINALIZADOS -> "Finalizado".equalsIgnoreCase(estadoTicket);
            default -> true;
        };
    }

    private boolean filtrarPorFecha(LocalDateTime fechaCreacion, LocalDate fecha) {

    if (fecha == null) {
        return true;
    }

    if (fechaCreacion == null) {
        return false;
    }

    return fechaCreacion.toLocalDate().isEqual(fecha);
}
    

}