package com.crm.gestiontickets.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "tbl_departamentos")
public class Departamentos {
    
    @Id
    @Column(name = "id_departamento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDepartamento;

    @Column(name = "nombre_departamento")
    private String nombreDepartamento;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

}
