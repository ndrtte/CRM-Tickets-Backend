package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.gestiontickets.entity.Agente;

public interface AgenteRepository extends JpaRepository<Agente, Integer>{
    
    public Agente findByUsuario(String usuario);

    //buscar por nombre, usuario o id_agente
    @Query("""
    SELECT a FROM Agente a
    WHERE CAST(a.idAgente AS string) LIKE %:valorBusqueda%
    OR LOWER(a.nombre) LIKE LOWER(CONCAT('%', :valorBusqueda, '%'))
    OR LOWER(a.apellido) LIKE LOWER(CONCAT('%', :valorBusqueda, '%'))
    OR LOWER(a.usuario) LIKE LOWER(CONCAT('%', :valorBusqueda, '%'))
    """)
    List<Agente> buscarPorCriterio(String valorBusqueda);
}
