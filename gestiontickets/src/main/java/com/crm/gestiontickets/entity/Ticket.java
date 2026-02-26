package com.crm.gestiontickets.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tbl_tickets")
public class Ticket {
    @Id
    @Column(name = "id_ticket")
    private String idTicket;

    @ManyToOne()
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne()
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne()
    @JoinColumn(name = "id_paso_actual_flujo")
    private PasoFlujo pasoActual;

    @ManyToOne()
    @JoinColumn(name = "id_agente_asignado")
    private Agente agenteAsignado;

    @ManyToOne()
    @JoinColumn(name = "id_estado_actual")
    private EstadoTicket estado;

    private boolean activo;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
