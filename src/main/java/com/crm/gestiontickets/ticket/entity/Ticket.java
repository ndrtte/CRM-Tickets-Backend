package com.crm.gestiontickets.ticket.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.cliente.entity.Cliente;

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
    @Column(name = "id_ticket", length=255)
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

    private Character activo;

    @Column(name = "fecha_creacion", insertable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion;

    public LocalDate toLocalDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toLocalDate'");
    }
}
