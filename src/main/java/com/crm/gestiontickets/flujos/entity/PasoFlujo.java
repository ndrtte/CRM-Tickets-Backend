package com.crm.gestiontickets.flujos.entity;

import com.crm.gestiontickets.agente.entity.Departamento;
import com.crm.gestiontickets.ticket.entity.Flujo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PASOS_FLUJO")
public class PasoFlujo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pasos_flujo")
    private Integer idPaso;

    @ManyToOne
    @JoinColumn(name = "id_flujo")
    private Flujo flujo;

    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;

    public Object getDescripcion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDescripcion'");
    }

    public void setDescripcion(Object descripcion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDescripcion'");
    }

    public Object getOrden() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrden'");
    }

    public void setOrden(Object orden) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setOrden'");
    }

    public void setFlujo(com.crm.gestiontickets.flujos.entity.Flujo flujo2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFlujo'");
    }

    public Integer getIdDepartamento() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdDepartamento'");
    }

    public void setIdDepartamento(Departamento orElseThrow) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setIdDepartamento'");
    }

    //deshabilitar / habilitar un flujo
    @Column(name = "estado")
    private Boolean estado = true;

    public Integer getIdPaso() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdPaso'");
    }

    public Boolean getEstado() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstado'");
    }

    public void setEstado(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEstado'");
    }

    public Object getDepartamento() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDepartamento'");
    }

    public void setDepartamento(Departamento departamento2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDepartamento'");
    }
}
