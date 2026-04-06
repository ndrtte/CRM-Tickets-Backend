package com.crm.gestiontickets.flujos.dto;

public class EtapaDTO {

    private String descripcion;
    private Integer idDepartamento;
    private Integer orden;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdDepartamento() { 
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) { 
        this.idDepartamento = idDepartamento;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getDepartamento() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDepartamento'");
    }
}