package com.crm.gestiontickets.service;

import java.time.LocalDateTime;
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
import com.crm.gestiontickets.entity.Nota;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.entity.Ticket;
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
    private HistoricoTicketRepository historicoTicketRepository;

    @Autowired
    private NotaRepository notaRepository;

    public IdTicket aperturaTicket(TicketApertura ticketAperturaDTO) {

        Ticket ticketApertura = new Ticket();

        String idTicket = secuencialTicketRepository.generarIdTicket();

        Agente agente = agenteRepository.findById(ticketAperturaDTO.getIdAgenteAsignado()).get();
        EstadoTicket estadosTicket = estadoTicketRepository.findByEstadoTicket("Nuevo");
        LocalDateTime fechaAsignacion = LocalDateTime.now();
        Cliente cliente = clienteRepository.findById(ticketAperturaDTO.getIdCliente()).get();
        Flujo flujoSistema = flujoRepository.findByDescripcion("FLUJO_SISTEMA");

        PasoFlujo pasoActual = flujoSistema.getPasos().get(0);

        ticketApertura.setIdTicket(idTicket);
        ticketApertura.setAgenteAsignado(agente);
        ticketApertura.setCliente(cliente);
        ticketApertura.setEstado(estadosTicket);
        ticketApertura.setPasoActual(pasoActual);
        ticketApertura.setActivo('S');
        ticketApertura.setFechaAsignacion(fechaAsignacion);

        ticketRepository.save(ticketApertura);

        IdTicket idTicketDTO = new IdTicket(idTicket);

        return idTicketDTO;
    }

    public IdTicket crearTicket(TicketCreacion nvoTicket) {
        Ticket ticket = ticketRepository.findById(nvoTicket.getIdTicket()).get();

        Agente agenteOrigen = ticket.getAgenteAsignado();
        Agente agenteDestino =  agenteRepository.findById(nvoTicket.getAgenteEjecutor()).get();

        Categoria categoria = categoriaRepository.findById(nvoTicket.getIdCategoria()).get();

        Flujo flujo = flujoRepository.findByCategoria(categoria);
        PasoFlujo primerPaso = obtenerPrimerPasoPendiente(flujo, ticket);

        PasoFlujo pasoAnterior = ticket.getPasoActual();

        ticket.setCategoria(categoria);
        ticket.setPasoActual(primerPaso);
        ticket.setEstado(estadoTicketRepository.findByEstadoTicket("En Proceso"));
        ticket.setFechaActualizacion(LocalDateTime.now());

        HistoricoTicket historico = registrarHistorico(ticket,agenteOrigen,agenteDestino,pasoAnterior,primerPaso);

        registrarNota(nvoTicket.getNota(), agenteOrigen, historico);

        ticketRepository.save(ticket);

        IdTicket idTicket = new IdTicket(ticket.getIdTicket());

        return idTicket;
    }

    //Logica provisional.
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

    //metodos auxiliares, todos sujetos a cambios. ojala me hubiera metido a psicologia

    private PasoFlujo obtenerPrimerPasoPendiente(Flujo flujo, Ticket ticket) {
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

    /* 
    private  void avanzarEtapa(String idTicket, Integer idAgenteEjecutor) {
        Ticket ticket = ticketRepository.findById(idTicket).get();

        Agente agenteOrigen = agenteRepository.findById(idAgenteEjecutor).get();
        Agente agenteDestino = ticket.getAgenteAsignado();

        PasoFlujo pasoActual = ticket.getPasoActual();
        PasoFlujo siguientePaso = obtenerSiguientePaso(ticket);

        ticket.setPasoActual(siguientePaso);
        ticket.setFechaActualizacion(LocalDateTime.now());

        registrarHistorico(ticket, agenteOrigen, agenteDestino, pasoActual, siguientePaso);

        ticketRepository.save(ticket);
    }*/

    /* 
    private PasoFlujo obtenerSiguientePaso(Ticket ticket) {
        List<PasoFlujo> pasos = pasoFlujoRepository.findByIdFlujoOrderByOrdenAsc(ticket.getPasoActual().getIdFlujo());

        for (int i = 0; i < pasos.size(); i++) {
            if (pasos.get(i).equals(ticket.getPasoActual()) && i + 1 < pasos.size()) {
                return pasos.get(i + 1);
            }
        }
        return ticket.getPasoActual();
    }*/

    private HistoricoTicket registrarHistorico(Ticket ticket, Agente agenteOrigen, Agente agenteDestino, PasoFlujo pasoOrigen, PasoFlujo pasoDestino) {
        HistoricoTicket historico = new HistoricoTicket();
        historico.setTicket(ticket);
        historico.setAgenteOrigen(agenteOrigen);
        historico.setAgenteDestino(agenteDestino);
        historico.setPasoOrigen(pasoOrigen);
        historico.setPasoDestino(pasoDestino);
        historico.setFechaHistorico(LocalDateTime.now());

        historicoTicketRepository.save(historico);

        return historico;
    }

    private void registrarNota(String nvoNota, Agente agente, HistoricoTicket historico) {
        Nota nota = new Nota();
        nota.setHistoricoTicket(historico);
        nota.setDescripcion(nvoNota);
        nota.setAgente(agente);
        notaRepository.save(nota);
    }

}
