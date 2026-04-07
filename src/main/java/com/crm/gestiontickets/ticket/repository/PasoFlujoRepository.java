package com.crm.gestiontickets.ticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;

@Repository
public interface PasoFlujoRepository extends JpaRepository<PasoFlujo, Integer> {

    // Buscar paso por flujo y orden
    PasoFlujo findByFlujoAndOrden(Flujo flujo, Integer orden);

    // Mostrar las etapas de un flujo 
    List<PasoFlujo> findByFlujoOrderByOrdenAsc(Flujo flujo);

    PasoFlujo findFirstByFlujoOrderByOrdenAsc(Flujo flujo);

    // Buscar por descripción
    Optional<PasoFlujo> findByDescripcion(String descripcion);

  
    List<PasoFlujo> findByFlujo_IdFlujoOrderByOrdenAsc(Integer idFlujo);
}