package com.crm.gestiontickets.service.ticket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.ticket.TicketDetalle;
import com.crm.gestiontickets.dto.ticket.TicketEtapaDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.Departamento;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.Flujo;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.enums.EstadoEtapaTicketEnum;
import com.crm.gestiontickets.enums.FiltroTicketsAgenteEnum;
import com.crm.gestiontickets.mapper.PasoFlujoMapper;
import com.crm.gestiontickets.mapper.TicketMapper;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.ClienteRepository;
import com.crm.gestiontickets.repository.EstadoTicketRepository;
import com.crm.gestiontickets.repository.FlujoRepository;
import com.crm.gestiontickets.repository.HistoricoTicketRepository;
import com.crm.gestiontickets.repository.TicketRepository;

@Service
public class TicketBusquedaService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FlujoRepository flujoRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private HistoricoTicketRepository historicoTicketRepository;

    @Autowired
    private PasoFlujoMapper pasoFlujoMapper;

    @Autowired
    private NotaService notaService;

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private EstadoTicketRepository estadoTicketRepository;

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

    public List<TicketDetalle> obtenerTicketsAgente(Integer idAgente, FiltroTicketsAgenteEnum filtroEstado) {

        Agente agente = agenteRepository.findById(idAgente).get();

        List<Ticket> tickets;

        List<TicketDetalle> response = new ArrayList<>();

        return response;
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
                ? ticket.getCategoria().getNombreCategoria()
                : "";

        Integer idCategoria = ticket.getCategoria() != null
                ? ticket.getCategoria().getIdCategoria()
                : null;

        boolean esPasoActual = ticket.getPasoActual().getIdPasosFlujo().equals(idPaso);

        List<HistoricoTicket> historicos = historicoTicketRepository
                .findHistoricoTicketByTicketYEtapa(ticket.getIdTicket(), idPaso);

        @SuppressWarnings(value = {""})
        PasoFlujo pasoConsulta;
        Departamento departamento;

        String agenteNombre = "Sin asignar";
        Integer idAgente = null;

        String nota = "";
        EstadoEtapaTicketEnum estado;

        if (esPasoActual) {

            estado = EstadoEtapaTicketEnum.EN_PROCESO;

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

            estado = EstadoEtapaTicketEnum.FINALIZADO;

            pasoConsulta = historico.getPasoDestino() != null
                    ? historico.getPasoDestino()
                    : historico.getPasoOrigen();

            departamento = pasoConsulta != null
                    ? pasoConsulta.getIdDepartamento()
                    : null;

            if (historico.getAgenteOrigen() != null) {
                agenteNombre = historico.getAgenteOrigen().getNombre() + " "
                        + historico.getAgenteOrigen().getApellido();

                idAgente = historico.getAgenteOrigen().getIdAgente();
            }

            nota = notaService.obtenerNotaPorHistorico(historico);
        } else {

            estado = EstadoEtapaTicketEnum.NO_INICIADO;

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

        String mensaje = estado == EstadoEtapaTicketEnum.NO_INICIADO
                ? "Etapa no iniciada o no asignada"
                : "Ok";

        return new Respuesta<>(true, mensaje, detalle);
    }

}
