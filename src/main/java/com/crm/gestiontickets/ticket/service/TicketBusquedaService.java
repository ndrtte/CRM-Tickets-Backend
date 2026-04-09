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
import com.crm.gestiontickets.ticket.enums.FiltroTicketAsignadosEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketEstadoEnum;
import com.crm.gestiontickets.ticket.enums.FiltroTicketsAgentesEnum;
import com.crm.gestiontickets.ticket.interfaces.FiltroTicketsAgenteStrategy;
import com.crm.gestiontickets.ticket.interfaces.IEstadoEtapaService;
import com.crm.gestiontickets.ticket.interfaces.IFechaUtils;
import com.crm.gestiontickets.ticket.interfaces.INotaService;
import com.crm.gestiontickets.ticket.interfaces.IPasoFlujoMapper;
import com.crm.gestiontickets.ticket.interfaces.ITicketBusquedaService;
import com.crm.gestiontickets.ticket.interfaces.ITicketMapper;
import com.crm.gestiontickets.ticket.repository.HistoricoTicketRepository;
import com.crm.gestiontickets.ticket.repository.PasoFlujoRepository;
import com.crm.gestiontickets.ticket.repository.TicketRepository;
import com.crm.gestiontickets.ticket.service.factory.FiltroFactory;

@Service
public class TicketBusquedaService implements ITicketBusquedaService{

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ITicketMapper ticketMapper;

    @Autowired
    private IPasoFlujoMapper pasoFlujoMapper;

    @Autowired
    private INotaService notaService;

    @Autowired
    private HistoricoTicketRepository historicoRepository;

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private PasoFlujoRepository pasoFlujoRepository;

    @Autowired
    private IEstadoEtapaService estadoEtapaService;

    @Autowired
    private IFechaUtils fechaUtils;

    @Autowired
    private FiltroFactory filtroFactory;

    @Override
    public TicketDetalle obtenerTicketDTO(String idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).get();
        return ticketMapper.mapearTicketADetalle(ticket);
    }

    @Override
    public Page<TicketDetalle> obtenerTicketsCliente(Long idCliente, int page, int pageSize,
            FiltroTicketEstadoEnum estado, FiltroFechaTicketEnum fechaOp, LocalDate fecha) {

        Cliente cliente = clienteRepository.findById(idCliente).get();
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("fechaCreacion").descending());
        String estadoStr = estado != null ? estado.getEstado() : null;

        LocalDateTime[] rango = fechaUtils.calcularRangoFecha(fecha, fechaOp);
        LocalDateTime fechaInicio = rango[0];
        LocalDateTime fechaFin = rango[1];

        Page<Ticket> ticketsPaginados = ticketRepository.findByClienteConFiltros(cliente, estadoStr, fechaInicio,
                fechaFin, pageable);

        return ticketsPaginados.map(ticketMapper::mapearTicketADetalle);
    }

    @Override
    public Page<TicketDetalle> obtenerTicketsDepartamento(Integer idDepartamento, int page, int pageSize,
            FiltroTicketAsignadosEnum asignacion) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Boolean asignado = asignacion != null ? asignacion == FiltroTicketAsignadosEnum.ASIGNADOS : null;
        Page<Ticket> ticketsPaginados = ticketRepository.findTicketsByDepartamento(idDepartamento, asignado, pageable);

        return ticketsPaginados.map(ticketMapper::mapearTicketADetalle);
    }

    @Override
    public Page<TicketEtapaAgenteDetalle> obtenerTicketsAgente(
            Integer idAgente,
            int page,
            int pageSize,
            FiltroTicketsAgentesEnum filtro,
            FiltroFechaTicketEnum fechaOp,
            LocalDate fecha) {

        Agente agente = agenteRepository.findById(idAgente).get();
        Pageable pageable = PageRequest.of(page, pageSize);

           //Principio de Open close
        FiltroTicketsAgenteStrategy strategy = filtroFactory.obtenerFiltro(filtro);

        return strategy.aplicar(agente, pageable, fechaOp, fecha);
    }

    @Override
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

}
