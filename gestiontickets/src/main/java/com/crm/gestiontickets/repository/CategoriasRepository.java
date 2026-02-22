package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Categorias;

public interface CategoriasRepository extends JpaRepository<Categorias, Integer>{
    
}
