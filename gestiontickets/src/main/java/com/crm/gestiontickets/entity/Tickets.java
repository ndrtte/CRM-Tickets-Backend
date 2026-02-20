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
public class Tickets {
    @Id
    @Column(name = "id_ticket", length = 20)
    private String idTicket;

    @ManyToOne()
    @JoinColumn(name = "id_cliente")
    private Clientes cliente;

    @ManyToOne()
    @JoinColumn(name = "id_categoria")
    private Categorias categoria;

    @ManyToOne()
    @JoinColumn(name = "id_paso_actual_flujo")
    private PasosFlujo pasoActual;

    @ManyToOne()
    @JoinColumn(name = "id_agente_asignado")
    private Agentes agenteAsignado;

    @ManyToOne()
    @JoinColumn(name = "id_estado_actual")
    private EstadosTicket estado;

    @Column(nullable = false, length = 1)
    private boolean activo;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}
