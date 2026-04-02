/*Patron: estructural: facade, obtine la informacion de un ticket y sus etapas */
package com.crm.gestiontickets.ticket.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.crm.gestiontickets.ticket.enums.FiltroFechaTicketEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketEstadoEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketsAgenteEnum;
import com.crm.gestiontickets.ticket.enums.TipoFechaEnum;
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
        Ticket ticket = ticketRepository.findById(idTicket).orElseThrow();
        return ticketMapper.mapearTicketADetalle(ticket);
    }

    public Page<TicketDetalle> obtenerTicketsCliente(Long idCliente, int page, int pageSize, FiltroTicketEstadoEnum estado, FiltroFechaTicketEnum fechaOp, LocalDate fecha) {
        Cliente cliente = clienteRepository.findById(idCliente).get();

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("fechaCreacion").descending());

        String estadoStr = estado != null ? estado.name() : null;

        LocalDateTime fechaInicio = null;
        LocalDateTime fechaFin = null;

        if (fecha != null && fechaOp != null) {
            switch (fechaOp) {
                case MAYOR ->
                    fechaInicio = fecha.atStartOfDay();
                case MENOR ->
                    fechaFin = fecha.atStartOfDay();
                case IGUAL -> {
                    fechaInicio = fecha.atStartOfDay();
                    fechaFin = fecha.atTime(23, 59, 59);
                }
            }
        }

        Page<Ticket> ticketsPaginados = ticketRepository.findByClienteConFiltros(
                cliente, estadoStr, fechaInicio, fechaFin, pageable
        );

        return ticketsPaginados.map(ticketMapper::mapearTicketADetalle);
    }

    public List<TicketDetalle> obtenerTicketsDepartamento(Integer idDepartamento) {
        return ticketRepository.findTicketsByDepartamento(idDepartamento)
                .stream()
                .map(ticketMapper::mapearTicketADetalle)
                .toList();
    }

    public List<TicketEtapaAgenteDetalle> obtenerTicketsAgente(
            Integer idAgente,
            FiltroTicketsAgenteEnum filtro) {

        Agente agente = agenteRepository.findById(idAgente).orElseThrow();

        return switch (filtro) {
            case EN_PROCESO -> ticketMapper.mapearEnProceso(agente);
            case FINALIZADOS -> ticketMapper.mapearFinalizados(agente);
            case TODOS -> ticketMapper.mapearTodos(agente);
        };
    }

    public Respuesta<TicketEtapaDetalle> obtenerEstadoTicketEtapa(String idTicket, Integer idPaso) {

        Ticket ticket = ticketRepository.findById(idTicket).orElseThrow();
        PasoFlujo pasoActual = ticket.getPasoActual();

        boolean ticketCerrado = ticket.getEstado().getEstadoTicket().equals("Cerrado");

        List<EtapaTicket> etapas = pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), pasoActual);

        boolean pasoValido = etapas.stream().anyMatch(e -> e.getIdPaso().equals(idPaso));

        if (!pasoValido) {
            return new Respuesta<>(false, "El paso no pertenece al flujo del ticket", null);
        }

        PasoFlujo paso = pasoFlujoRepository.findById(idPaso).orElseThrow();

        EstadoEtapaTicketEnum estado = estadoEtapaService.obtenerEstado(
                ticket, paso, pasoActual, ticketCerrado);

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

    public List<TicketDetalle> obtenerTicketsClienteFiltro(
            Long idCliente,
            FiltroTicketsAgenteEnum estado,
            LocalDate fecha) {

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow();

        return ticketRepository.findByCliente(cliente)
                .stream()
                .filter(t -> filtrarPorEstado(t, estado))
                .filter(t -> filtrarPorFecha(t.getFechaCreacion(), fecha))
                .map(ticketMapper::mapearTicketADetalle)
                .toList();
    }

    private boolean filtrarPorEstado(Ticket ticket, FiltroTicketsAgenteEnum estado) {

        if (ticket.getEstado() == null) return false;

        String estadoTicket = ticket.getEstado().getEstadoTicket();

        return switch (estado) {
            case EN_PROCESO -> "En Proceso".equalsIgnoreCase(estadoTicket);
            case FINALIZADOS -> "Finalizado".equalsIgnoreCase(estadoTicket);
            default -> true;
        };
    }

    private boolean filtrarPorFecha(LocalDateTime fechaCreacion, LocalDate fecha) {

        if (fecha == null) return true;
        if (fechaCreacion == null) return false;

        return fechaCreacion.toLocalDate().isEqual(fecha);
    }



    public List<TicketDetalle> obtenerTicketsPorDepartamentoFiltro(
            Integer idDepartamento,
            String estado,
            TipoFechaEnum fechaOp,
            LocalDate fecha) {

        List<Ticket> tickets = ticketRepository.buscarTicketsFiltrados(idDepartamento, estado);

        return tickets.stream()
                .filter(t -> filtrarPorFecha(t.getFechaCreacion(), fechaOp, fecha))
                .map(ticketMapper::mapearTicketADetalle)
                .toList();
    }

    private boolean filtrarPorFecha(LocalDateTime fechaCreacion, TipoFechaEnum op, LocalDate fecha) {

        if (fecha == null || op == null) return true;

        LocalDate fechaTicket = fechaCreacion.toLocalDate();

        return switch (op) {
            case MENOR -> fechaTicket.isBefore(fecha);
            case IGUAL -> fechaTicket.isEqual(fecha);
            case MAYOR -> fechaTicket.isAfter(fecha);
        };
    }
}