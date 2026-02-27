package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Flujo;

public interface FlujoRepository extends JpaRepository<Flujo, Integer> {
    
    public Flujo findByCategoria(Categoria categoria);

}
