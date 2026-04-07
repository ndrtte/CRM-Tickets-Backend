package com.crm.gestiontickets.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.Flujo;

@Repository
public interface FlujoRepository extends JpaRepository<Flujo, Integer> {

    // Obtener flujo por categoría
    Flujo findByCategoria(Categoria categoria);

    boolean existsByCategoria_IdCategoria(Integer idCategoria);
}