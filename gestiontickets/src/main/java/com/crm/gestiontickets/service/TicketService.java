package com.crm.gestiontickets.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.IdTicket;
import com.crm.gestiontickets.dto.TicketApertura;
import com.crm.gestiontickets.dto.TicketCreacion;
import com.crm.gestiontickets.dto.TicketDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.Departamento;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.entity.Flujo;
import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.CategoriaRepository;
import com.crm.gestiontickets.repository.ClienteRepository;
import com.crm.gestiontickets.repository.DepartamentoRepository;
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
    private HistoricoTicketRepository historicoTicketRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

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

        registrarHistorico(ticket, null, agenteDestino, null, primerPaso);

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

    private void registrarHistorico(Ticket ticket, Agente agenteOrigen, Agente agenteDestino, PasoFlujo pasoOrigen, PasoFlujo pasoDestino) {
        HistoricoTicket historico = new HistoricoTicket();
        historico.setTicket(ticket);
        historico.setAgenteOrigen(agenteOrigen);
        historico.setAgenteDestino(agenteDestino);
        historico.setPasoOrigen(pasoOrigen);
        historico.setPasoDestino(pasoDestino);

        historicoTicketRepository.save(historico);
    }

    public TicketDetalle obtenerTicketDTO(String idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket).get();

        Cliente cliente = ticket.getCliente();
        Categoria categoria = ticket.getCategoria();
        PasoFlujo pasoActual = ticket.getPasoActual();
        Agente agente = ticket.getAgenteAsignado();
        EstadoTicket estado = ticket.getEstado();
        Departamento departamento = ticket.getPasoActual().getIdDepartamento();
        TicketDetalle ticketDetalle = new TicketDetalle();

        ticketDetalle.setIdTicket(idTicket);
        ticketDetalle.setIdCliente(cliente.getIdCliente());
        ticketDetalle.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
        ticketDetalle.setIdCategoria(categoria.getIdCategoria());
        ticketDetalle.setCategoria(categoria.getNombre());
        ticketDetalle.setIdPasoActual(pasoActual.getIdPasosFlujo());
        ticketDetalle.setPasoActual(pasoActual.getDescripcion());
        ticketDetalle.setIdAgente(agente.getIdAgente());
        ticketDetalle.setNombreAgente(agente.getNombre() + " " + agente.getApellido());
        ticketDetalle.setIdEstado(estado.getIdEstadoTicket());
        ticketDetalle.setEstado(estado.getEstadoTicket());
        ticketDetalle.setFechaCreacion(ticket.getFechaCreacion());
        ticketDetalle.setIdDepartamento(departamento.getIdDepartamento());
        ticketDetalle.setDepartamento(departamento.getNombreDepartamento());

        return ticketDetalle;
    }

    public List<TicketDetalle> obtenerTicketsCliente(Long idCliente) {
        List<TicketDetalle> listaTicketsDTO = new ArrayList<>();

        Cliente cliente = clienteRepository.findById(idCliente).get();

        List<Ticket> listaTicket = ticketRepository.findByCliente(cliente);

        for (Ticket ticket : listaTicket) {
            TicketDetalle ticketDetalle = obtenerTicketDTO(ticket.getIdTicket());
            listaTicketsDTO.add(ticketDetalle);
        }

        return listaTicketsDTO;
    }

    public List<TicketDetalle> obtenerTicketsDepartamento(Integer idDepartamento) {

        List<TicketDetalle> listaTicketsDTO = new ArrayList<>();

        List<Ticket> listaTicket = ticketRepository.findTicketsByDepartamento(idDepartamento);

        for (Ticket ticket : listaTicket) {
            TicketDetalle ticketDetalle = obtenerTicketDTO(ticket.getIdTicket());
            listaTicketsDTO.add(ticketDetalle);
        }

        return listaTicketsDTO;
    }

}
