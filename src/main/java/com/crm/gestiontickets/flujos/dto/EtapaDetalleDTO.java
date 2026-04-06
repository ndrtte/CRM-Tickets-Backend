package com.crm.gestiontickets.flujos.dto;

public class EtapaDetalleDTO {

    private Integer idPaso;
    private String descripcion;
    private Integer orden;
    private Boolean estado;
    private String nombreDepartamento;

    public Integer getIdPaso() {
        return idPaso;
    }

    public void setIdPaso(Integer idPaso) {
        this.idPaso = idPaso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Object descripcion) { 
        this.descripcion = (String) descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Object orden) { 
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }
}