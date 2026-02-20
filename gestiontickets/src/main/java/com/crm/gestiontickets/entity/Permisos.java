package com.crm.gestiontickets.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "tbl_permisos")
public class Permisos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_ticket")
    private int idEstadoTicket;

    @Column(name = "estado_ticket")
    private String estadoTicket;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany()
    @JoinTable(
        name = "tbl_roles_permisos",
        joinColumns = @JoinColumn(name = "id_permiso"),
        inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private List<Roles> idRoles;

}
