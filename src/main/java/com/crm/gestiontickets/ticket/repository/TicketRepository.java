package com.crm.gestiontickets.ticket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.cliente.entity.Cliente;
import com.crm.gestiontickets.reportes.dto.ReporteTicketDTO;
import com.crm.gestiontickets.ticket.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    // =========================
    // 🔎 FILTRO POR CLIENTE
    // =========================
    @Query("""
        SELECT t FROM Ticket t
        WHERE t.cliente = :cliente
        AND (:estado IS NULL OR t.estado.estadoTicket = :estado)
        AND (:fechaInicio IS NULL OR t.fechaCreacion >= :fechaInicio)
        AND (:fechaFin IS NULL OR t.fechaCreacion <= :fechaFin)
    """)
    Page<Ticket> findByClienteConFiltros(
            @Param("cliente") Cliente cliente,
            @Param("estado") String estado,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            Pageable pageable
    );

    // =========================
    // 🏢 TICKETS POR DEPARTAMENTO
    // =========================
    @Query("""
        SELECT t FROM Ticket t
        WHERE t.pasoActual.idDepartamento.idDepartamento = :idDepartamento
        AND (:activo IS NULL OR t.activo = :activo)
    """)
    Page<Ticket> findTicketsByDepartamento(
            @Param("idDepartamento") Integer idDepartamento,
            @Param("activo") Boolean activo,
            Pageable pageable
    );

    // Versión simple (sin paginación)
    @Query("""
        SELECT t FROM Ticket t
        WHERE t.pasoActual.idDepartamento.idDepartamento = :idDepartamento
    """)
    List<Ticket> findTicketsByDepartamento(@Param("idDepartamento") Integer idDepartamento);

    // =========================
    // 👨‍💼 TICKETS POR AGENTE
    // =========================
    List<Ticket> findByAgenteAsignado(Agente agenteAsignado);

    List<Ticket> findByAgenteAsignadoAndEstado(Agente agenteAsignado, com.crm.gestiontickets.ticket.entity.EstadoTicket estado);

    // =========================
    // 🔍 FILTRO POR ESTADO + TIEMPO
    // =========================
    @Query("""
        SELECT t FROM Ticket t
        WHERE t.agenteAsignado = :agente
        AND (:cerrado IS NULL
             OR (:cerrado = TRUE AND t.estado.estadoTicket = 'Cerrado')
             OR (:cerrado = FALSE AND (t.estado IS NULL OR t.estado.estadoTicket <> 'Cerrado')))
        AND (:inicio IS NULL OR t.fechaCreacion >= :inicio)
        AND (:fin IS NULL OR t.fechaCreacion < :fin)
    """)
    Page<Ticket> findTicketsByEstado(
            @Param("agente") Agente agente,
            @Param("cerrado") Boolean cerrado,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin,
            Pageable pageable
    );

    // =========================
    // 📊 MÉTRICAS (REPORTES)
    // =========================

    @Query(value = """
        SELECT AVG(
            CAST(DATEDIFF(MINUTE, t.fecha_creacion, t.fecha_actualizacion) AS FLOAT) / 60.0
        )
        FROM TBL_TICKETS t
        INNER JOIN TBL_ESTADOS_TICKET e ON t.id_estado_actual = e.id_estado_ticket
        WHERE t.fecha_actualizacion IS NOT NULL
        AND e.estado_ticket IN ('Cerrado', 'Resuelto')
    """, nativeQuery = true)
    Double promedioResolucionGlobalHoras();

    @Query(value = """
        SELECT AVG(
            CAST(DATEDIFF(MINUTE, t.fecha_creacion, t.fecha_asignacion) AS FLOAT) / 60.0
        )
        FROM TBL_TICKETS t
        WHERE t.fecha_asignacion IS NOT NULL
    """, nativeQuery = true)
    Double promedioPrimeraRespuestaHoras();

    @Query(value = """
        SELECT AVG(
            CAST(DATEDIFF(MINUTE, t.fecha_creacion, SYSDATETIME()) AS FLOAT) / 60.0
        )
        FROM TBL_TICKETS t
        INNER JOIN TBL_ESTADOS_TICKET e ON t.id_estado_actual = e.id_estado_ticket
        WHERE e.estado_ticket NOT IN ('Cerrado', 'Resuelto')
        AND t.activo = 'S'
    """, nativeQuery = true)
    Double promedioTiempoAbiertoHoras();

    @Query(value = """
        SELECT
            CONCAT(a.nombre, ' ', a.apellido) AS agenteNombre,
            AVG(CAST(DATEDIFF(MINUTE, t.fecha_creacion, t.fecha_actualizacion) AS FLOAT) / 60.0) AS promedioResolucionHoras,
            AVG(CAST(DATEDIFF(MINUTE, t.fecha_creacion, t.fecha_asignacion) AS FLOAT) / 60.0) AS promedioPrimeraRespuestaHoras,
            AVG(CAST(DATEDIFF(MINUTE, t.fecha_creacion, SYSDATETIME()) AS FLOAT) / 60.0) AS promedioTiempoAbiertoHoras,
            CAST(COUNT(t.id_ticket) AS BIGINT) AS totalTickets
        FROM TBL_TICKETS t
        INNER JOIN TBL_AGENTES a ON t.id_agente_asignado = a.id_agente
        WHERE t.id_agente_asignado IS NOT NULL
        GROUP BY a.id_agente, a.nombre, a.apellido
        ORDER BY promedioResolucionHoras ASC
    """, nativeQuery = true)
    List<ReporteTicketDTO> estadisticasPorAgente();

    @Query(value = """
        SELECT
            e.estado_ticket AS estadoTicket,
            COUNT(t.id_ticket) AS total
        FROM TBL_TICKETS t
        INNER JOIN TBL_ESTADOS_TICKET e ON t.id_estado_actual = e.id_estado_ticket
        GROUP BY e.estado_ticket
    """, nativeQuery = true)
    List<Object[]> conteoPorEstado();

    @Query(value = """
        SELECT
            FORMAT(fecha_creacion, 'yyyy-MM') AS mes,
            COUNT(*) AS total
        FROM TBL_TICKETS
        WHERE fecha_creacion >= DATEADD(MONTH, -6, SYSDATETIME())
        GROUP BY FORMAT(fecha_creacion, 'yyyy-MM')
        ORDER BY mes ASC
    """, nativeQuery = true)
    List<Object[]> ticketsPorMes();
}
