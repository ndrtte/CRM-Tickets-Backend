package com.crm.gestiontickets.flujos.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.gestiontickets.flujos.entity.Flujo;

@Repository
public interface FlujoRepository extends JpaRepository<Flujo, Integer> {

    // 🔍 Verifica si ya existe un flujo para una categoría (regla UNIQUE de tu BD)
    boolean existsByCategoria_IdCategoria(Integer idCategoria);

}