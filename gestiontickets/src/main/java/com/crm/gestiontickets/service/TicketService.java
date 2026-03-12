package com.crm.gestiontickets.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.IdTicket;
import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.TicketApertura;
import com.crm.gestiontickets.dto.TicketCreacion;
import com.crm.gestiontickets.dto.TicketDetalle;
import com.crm.gestiontickets.dto.TicketEtapaDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.Departamento;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.Flujo;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.enums.EstadoEtapaTicket;
import com.crm.gestiontickets.mapper.PasoFlujoMapper;
import com.crm.gestiontickets.mapper.TicketMapper;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.CategoriaRepository;
import com.crm.gestiontickets.repository.ClienteRepository;
import com.crm.gestiontickets.repository.EstadoTicketRepository;
import com.crm.gestiontickets.repository.FlujoRepository;
import com.crm.gestiontickets.repository.HistoricoTicketRepository;
import com.crm.gestiontickets.repository.PasoFlujoRepository;
import com.crm.gestiontickets.repository.SecuencialTicketRepository;
import com.crm.gestiontickets.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EstadoTicketRepository estadoTicketRepository;

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private SecuencialTicketRepository secuencialTicketRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PasoFlujoRepository pasoFlujoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FlujoRepository flujoRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private HistorialTicketService historialTicketService;

    @Autowired
    private HistoricoTicketRepository historicoTicketRepository;

    @Autowired
    private PasoFlujoMapper pasoFlujoMapper;

    @Autowired
    private NotaService notaService;

    public Respuesta<IdTicket> aperturaTicket(TicketApertura dto) {

        Ticket ticket = new Ticket();

        String idTicket = secuencialTicketRepository.generarIdTicket();

        Agente agente = agenteRepository.findById(dto.getIdAgenteAsignado()).get();
        Cliente cliente = clienteRepository.findById(dto.getIdCliente()).get();
        EstadoTicket estadoNuevo = estadoTicketRepository.findByEstadoTicket("Nuevo");

        LocalDateTime ahora = LocalDateTime.now();

        ticket.setIdTicket(idTicket);
        ticket.setAgenteAsignado(agente);
        ticket.setCliente(cliente);
        ticket.setEstado(estadoNuevo);
        ticket.setActivo('S');
        ticket.setFechaAsignacion(ahora);

        ticketRepository.save(ticket);

        IdTicket resultado = new IdTicket(idTicket);

        return new Respuesta<>(true, "Ticket abierto correctamente", resultado);
    }

    public Respuesta<IdTicket> crearTicket(TicketCreacion dto) {

        Ticket ticket = ticketRepository.findById(dto.getIdTicket()).get();

        Agente agenteOrigen = ticket.getAgenteAsignado();

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria()).get();
        ticket.setCategoria(categoria);

        Flujo flujo = flujoRepository.findByCategoria(categoria);

        PasoFlujo primerPaso = pasoFlujoRepository.findFirstByIdFlujoOrderByOrdenAsc(flujo);
        ticket.setPasoActual(primerPaso);

        EstadoTicket estadoProceso = estadoTicketRepository.findByEstadoTicket("En Proceso");
        ticket.setEstado(estadoProceso);

        ticket.setFechaActualizacion(LocalDateTime.now());

        HistoricoTicket historico = historialTicketService.registrarHistorico(
                ticket,
                agenteOrigen,
                null,
                ticket.getPasoActual(),
                primerPaso);

        notaService.registrarNota(dto.getNota(), historico);

        ticketRepository.save(ticket);

        return new Respuesta<>(true, "Ticket creado correctamente", new IdTicket(ticket.getIdTicket()));
    }

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

    public Respuesta<TicketEtapaDetalle> obtenerEstadoTicketEtapa(String idTicket, Integer idPaso) {

        Ticket ticket = ticketRepository.findById(idTicket).get();

        Flujo flujo = flujoRepository.findByCategoria(ticket.getCategoria());

        boolean pasoValido = flujo.getPasos().stream()
                .anyMatch(p -> p.getIdPasosFlujo().equals(idPaso));

        if (!pasoValido) {
            return new Respuesta<>(false, "Esta etapa no pertenece al flujo del ticket", null);
        }

        TicketEtapaDetalle detalle = new TicketEtapaDetalle();
        detalle.setIdTicket(idTicket);

        Cliente cliente = ticket.getCliente();
        detalle.setIdCliente(cliente.getIdCliente());
        detalle.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());

        String categoria = ticket.getCategoria() != null
                ? ticket.getCategoria().getNombre()
                : "";

        Integer idCategoria = ticket.getCategoria() != null
                ? ticket.getCategoria().getIdCategoria()
                : null;

        boolean esPasoActual = ticket.getPasoActual().getIdPasosFlujo().equals(idPaso);

        List<HistoricoTicket> historicos = historicoTicketRepository
                .findHistoricoTicketByTicketYEtapa(ticket.getIdTicket(), idPaso);

        PasoFlujo pasoConsulta = null;
        Departamento departamento = null;

        String agenteNombre = "Sin asignar";
        Integer idAgente = null;

        String nota = "";
        EstadoEtapaTicket estado;

        if (esPasoActual) {

            estado = EstadoEtapaTicket.EN_PROCESO;

            pasoConsulta = ticket.getPasoActual();
            departamento = pasoConsulta.getIdDepartamento();

            if (ticket.getAgenteAsignado() != null) {
                agenteNombre = ticket.getAgenteAsignado().getNombre() + " "
                        + ticket.getAgenteAsignado().getApellido();

                idAgente = ticket.getAgenteAsignado().getIdAgente();
            }

            if (!historicos.isEmpty()) {
                nota = notaService.obtenerNotaPorHistorico(historicos.get(0));
            }

        } else if (!historicos.isEmpty()) {

            HistoricoTicket historico = historicos.get(0);

            estado = EstadoEtapaTicket.FINALIZADO;

            pasoConsulta = historico.getPasoDestino() != null
                    ? historico.getPasoDestino()
                    : historico.getPasoOrigen();

            departamento = pasoConsulta != null
                    ? pasoConsulta.getIdDepartamento()
                    : null;

            if (historico.getAgenteDestino() != null) {
                agenteNombre = historico.getAgenteDestino().getNombre() + " "
                        + historico.getAgenteDestino().getApellido();

                idAgente = historico.getAgenteDestino().getIdAgente();
            }

            nota = notaService.obtenerNotaPorHistorico(historico);

        } else {

            estado = EstadoEtapaTicket.NO_INICIADO;

            pasoConsulta = new PasoFlujo();
            pasoConsulta.setDescripcion("Etapa no iniciada");

            departamento = new Departamento();
            departamento.setNombreDepartamento("Sin asignar");
        }

        Integer idDepartamento = departamento != null
                ? departamento.getIdDepartamento()
                : null;

        detalle.setIdCategoria(idCategoria);
        detalle.setIdAgente(idAgente);
        detalle.setIdDepartamento(idDepartamento);

        detalle.setCategoria(categoria);
        detalle.setPasoActual(pasoConsulta != null ? pasoConsulta.getDescripcion() : "Desconocido");
        detalle.setDepartamento(departamento != null ? departamento.getNombreDepartamento() : "Desconocido");
        detalle.setNombreAgente(agenteNombre);
        detalle.setNota(nota);
        detalle.setEstadoEtapa(estado);
        detalle.setListaEtapas(pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), ticket.getPasoActual()));

        String mensaje = estado == EstadoEtapaTicket.NO_INICIADO
                ? "Etapa no iniciada o no asignada"
                : "Ok";

        return new Respuesta<>(true, mensaje, detalle);
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

}
