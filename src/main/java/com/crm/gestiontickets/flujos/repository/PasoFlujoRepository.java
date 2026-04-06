package com.crm.gestiontickets.flujos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.gestiontickets.flujos.entity.PasoFlujo;

@Repository
public interface PasoFlujoRepository extends JpaRepository<PasoFlujo, Integer> {

    // 🔍 Obtener todas las etapas de un flujo
    List<PasoFlujo> findByFlujo_IdFlujoOrderByOrdenAsc(Integer idFlujo);

    //deshabilitar un flujo
    @Modifying
    @Query("UPDATE Flujo f SET f.estado = false WHERE f.idFlujo = :idFlujo")
    void deshabilitarFlujo(@Param("idFlujo") Integer idFlujo);

    //deshabilitar / habilitar un flujo

    @Modifying
    @Query("UPDATE PasoFlujo p SET p.estado = :estado WHERE p.idPaso = :idPaso")
    void actualizarEstadoPaso(@Param("idPaso") Integer idPaso, @Param("estado") Boolean estado);
}