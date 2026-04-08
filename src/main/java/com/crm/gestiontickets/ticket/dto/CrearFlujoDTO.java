package com.crm.gestiontickets.ticket.dto;

import java.util.List;

public class CrearFlujoDTO {

    private String descripcion;
    private Integer idCategoria;
    private List<EtapaDTO> etapas;

    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public List<EtapaDTO> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<EtapaDTO> etapas) {
        this.etapas = etapas;
    }
}
