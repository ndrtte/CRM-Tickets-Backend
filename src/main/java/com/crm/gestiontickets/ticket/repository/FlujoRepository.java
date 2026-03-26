package com.crm.gestiontickets.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.Flujo;

public interface FlujoRepository extends JpaRepository<Flujo, Integer> {
    
    public Flujo findByCategoria(Categoria categoria);

}
