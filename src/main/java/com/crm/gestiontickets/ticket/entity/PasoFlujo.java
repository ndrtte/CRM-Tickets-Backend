package com.crm.gestiontickets.ticket.entity;

import com.crm.gestiontickets.agente.entity.Departamento;
import com.crm.gestiontickets.ticket.dto.BaseTicketDetalle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_pasos_flujo")
public class PasoFlujo {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pasos_flujo")
    private Integer idPasosFlujo;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_flujo")
    private Flujo flujo;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    @Column(name = "estado")
    private Boolean estado = true;

    public BaseTicketDetalle getIdDepartamento() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdDepartamento'");
    }
     public void setEstado(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEstado'");
    }

    public Object getEstado() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstado'");
    }

}