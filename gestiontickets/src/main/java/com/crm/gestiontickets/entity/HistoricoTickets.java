package com.crm.gestiontickets.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_historico_tickets")
public class HistoricoTickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico_tickets")
    private Integer idHistoricoTickets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket")
    private Tickets ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agente_origen")
    private Agentes agenteOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agente_destino")
    private Agentes agenteDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paso_origen")
    private PasosFlujo pasoOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paso_destino")
    private PasosFlujo pasoDestino;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion;

}
