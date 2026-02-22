package com.crm.gestiontickets.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
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
@Table(name = "tbl_flujos")
public class Flujos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_flujo")
    private Integer idFlujo;

    private String descripcion;

    @OneToOne
    @JoinColumn(name = "id_categoria")
    private Categorias categoria;

    @OneToMany(mappedBy = "idFlujo", cascade = CascadeType.ALL)
    @OrderBy("orden ASC")
    private List<PasosFlujo> pasos;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
