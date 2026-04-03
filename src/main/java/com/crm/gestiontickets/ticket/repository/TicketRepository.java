package com.crm.gestiontickets.ticket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.cliente.entity.Cliente;
import com.crm.gestiontickets.ticket.entity.EstadoTicket;
import com.crm.gestiontickets.ticket.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, String>, JpaSpecificationExecutor<Ticket> {

    @Query("SELECT t FROM Ticket t "
            + "WHERE t.cliente = :cliente "
            + "AND (:estado IS NULL OR t.estado.estadoTicket = :estado) "
            + "AND (:fechaInicio IS NULL OR :fechaFin IS NULL OR t.fechaCreacion BETWEEN :fechaInicio AND :fechaFin)")
    Page<Ticket> findByClienteConFiltros(
            @Param("cliente") Cliente cliente,
            @Param("estado") String estado,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            Pageable pageable
    );

    @Query("""
        SELECT t 
        FROM Ticket t
        WHERE t.pasoActual.idDepartamento.idDepartamento = :idDepartamento
        """)
    List<Ticket> findTicketsByDepartamento(Integer idDepartamento);

    List<Ticket> findByAgenteAsignado(Integer idAgente);

    List<Ticket> findByAgenteAsignadoAndEstado(Agente agenteAsignado, EstadoTicket estado);

    public List<Ticket> findByAgenteAsignado(Agente agente);

    List<Ticket> findByPasoActual_IdDepartamento_IdDepartamento(Integer idDepartamento);

    @Query("""
    SELECT t FROM Ticket t
    WHERE t.pasoActual.idDepartamento.idDepartamento = :idDepartamento
    AND (:estado IS NULL OR t.estado.estadoTicket = :estado)
    """)
    List<Ticket> buscarTicketsFiltrados(
        @Param("idDepartamento") Integer idDepartamento,
        @Param("estado") String estado
    );

    List<Ticket> findByCliente(Cliente cliente);
    }

