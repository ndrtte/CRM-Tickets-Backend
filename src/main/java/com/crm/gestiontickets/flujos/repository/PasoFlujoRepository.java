package com.crm.gestiontickets.flujos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.gestiontickets.flujos.entity.PasoFlujo;

@Repository
public interface PasoFlujoRepository extends JpaRepository<PasoFlujo, Integer> {

    // 🔍 Obtener todas las etapas de un flujo
    List<PasoFlujo> findByFlujo_IdFlujoOrderByOrdenAsc(Integer idFlujo);

}