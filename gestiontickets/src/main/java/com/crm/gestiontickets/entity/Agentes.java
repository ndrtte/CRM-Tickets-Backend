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
@Table(name = "tbl_agentes")
public class Agentes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agente")
    private Integer idAgente;

    private String nombre;

    private String apellido;

    private String usuario;

    private String contrasenia;

    private Character activo;

    @ManyToOne(fetch = FetchType.LAZY) //Sujeta a cambios
    @JoinColumn(name = "id_rol", nullable = false)
    private Roles rol;

    @ManyToOne(fetch = FetchType.LAZY) //Sujeta a cambios x2
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamentos departamento;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}

