package com.crm.gestiontickets.flujos.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.gestiontickets.flujos.entity.Flujo;

@Repository
public interface FlujoRepository extends JpaRepository<Flujo, Integer> {

    // 🔍 Verifica si ya existe un flujo para una categoría (regla UNIQUE de tu BD)
    boolean existsByCategoria_IdCategoria(Integer idCategoria);
    @Modifying
    @Query("UPDATE Flujo f SET f.estado = false WHERE f.idFlujo = :idFlujo")
    void deshabilitarFlujo(@Param("idFlujo") Integer idFlujo);
}

