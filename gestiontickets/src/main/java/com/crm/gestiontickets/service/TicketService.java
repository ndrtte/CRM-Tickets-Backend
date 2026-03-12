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
import com.crm.gestiontickets.entity.Nota;
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
import com.crm.gestiontickets.repository.NotaRepository;
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
    private NotaRepository notaRepository;

    @Autowired
    private PasoFlujoMapper pasoFlujoMapper;

    public IdTicket aperturaTicket(TicketApertura ticketAperturaDTO) {

        Ticket ticketArpetura = new Ticket();

        String idTicket = secuencialTicketRepository.generarIdTicket();

        Agente agente = agenteRepository.findById(ticketAperturaDTO.getIdAgenteAsignado()).get();
        EstadoTicket estadosTicket = estadoTicketRepository.findByEstadoTicket("Nuevo");
        LocalDateTime fechaAsignacion = LocalDateTime.now();
        Cliente cliente = clienteRepository.findById(ticketAperturaDTO.getIdCliente()).get();

        ticketArpetura.setIdTicket(idTicket);
        ticketArpetura.setAgenteAsignado(agente);
        ticketArpetura.setCliente(cliente);
        ticketArpetura.setEstado(estadosTicket);
        ticketArpetura.setActivo('S');
        ticketArpetura.setFechaAsignacion(fechaAsignacion);

        ticketRepository.save(ticketArpetura);

        IdTicket idTicketDTO = new IdTicket(idTicket);

        return idTicketDTO;
    }

    public IdTicket crearTicket(TicketCreacion nvoTicket) {

        Ticket ticket = ticketRepository.findById(nvoTicket.getIdTicket()).get();

        Agente agenteDestino = ticket.getAgenteAsignado();

        Categoria categoria = categoriaRepository.findById(nvoTicket.getIdCategoria()).get();
        ticket.setCategoria(categoria);

        Flujo flujo = flujoRepository.findByCategoria(categoria);

        PasoFlujo primerPaso = getPrimerPasoPendiente(flujo, ticket);
        ticket.setPasoActual(primerPaso);

        ticket.setEstado(estadoTicketRepository.findByEstadoTicket("En Proceso"));

        ticket.setFechaActualizacion(LocalDateTime.now());

        historialTicketService.registrarHistorico(ticket, null, agenteDestino, null, primerPaso);

        ticketRepository.save(ticket);

        return new IdTicket(ticket.getIdTicket());
    }

    private PasoFlujo getPrimerPasoPendiente(Flujo flujo, Ticket ticket) {
        List<PasoFlujo> pasos = pasoFlujoRepository.findByIdFlujoOrderByOrdenAsc(flujo);
        for (PasoFlujo paso : pasos) {
            if (!pasoCompletado(ticket, paso)) {
                return paso;
            }
        }
        return pasos.get(0);
    }

    private boolean pasoCompletado(Ticket ticket, PasoFlujo paso) {
        return historicoTicketRepository.existsByTicketAndPasoDestino(ticket, paso);
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

        boolean pasoValido = false;

        for (PasoFlujo paso : flujo.getPasos()) {
            if (paso.getIdPasosFlujo().equals(idPaso)) {
                pasoValido = true;
                break;
            }
        }

        if (!pasoValido) {
            return new Respuesta<>(false, "Esta etapa no pertenece al flujo del ticket", null);
        }
        
        TicketEtapaDetalle detalle = new TicketEtapaDetalle();
        detalle.setIdTicket(idTicket);

        Cliente cliente = ticket.getCliente();
        detalle.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());

        String categoria = ticket.getCategoria() != null
                ? ticket.getCategoria().getNombre()
                : "";

        PasoFlujo pasoConsulta;
        Departamento departamento;
        String agenteNombre = "Sin asignar";
        String nota = "";
        EstadoEtapaTicket estado;

        boolean esPasoActual = ticket.getPasoActual().getIdPasosFlujo().equals(idPaso);

        if (esPasoActual) {

            estado = EstadoEtapaTicket.EN_PROCESO;

            pasoConsulta = ticket.getPasoActual();
            departamento = pasoConsulta.getIdDepartamento();

            if (ticket.getAgenteAsignado() != null) {
                agenteNombre = ticket.getAgenteAsignado().getNombre() + " "
                        + ticket.getAgenteAsignado().getApellido();
            }

        } else {

            List<HistoricoTicket> historicos = historicoTicketRepository
                    .findHistoricoTicketByTicketYEtapa(ticket.getIdTicket(), idPaso);

            if (!historicos.isEmpty()) {

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
                }

                List<Nota> notas = notaRepository.findNotasByHistoricoTicket(historico);

                if (!notas.isEmpty()) {
                    nota = notas.get(0).getDescripcion();
                }

            } else {

                estado = EstadoEtapaTicket.NO_INICIADO;

                pasoConsulta = new PasoFlujo();
                pasoConsulta.setDescripcion("Etapa no iniciada");

                departamento = new Departamento();
                departamento.setNombreDepartamento("Sin asignar");
            }
        }

        detalle.setCategoria(categoria);
        detalle.setPasoActual(pasoConsulta != null ? pasoConsulta.getDescripcion() : "Desconocido");
        detalle.setDepartamento(departamento != null ? departamento.getNombreDepartamento() : "Desconocido");
        detalle.setAgente(agenteNombre);
        detalle.setNota(nota);
        detalle.setEstadoTicket(estado);
        detalle.setListaEtapas(pasoFlujoMapper.mapearEtapas(ticket.getCategoria(), pasoConsulta));

        boolean etapaIniciada = estado != EstadoEtapaTicket.NO_INICIADO;

        String mensaje = etapaIniciada ? "Ok" : "Etapa no iniciada o no asignada";

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
