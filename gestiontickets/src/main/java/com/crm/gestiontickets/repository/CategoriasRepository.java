package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Categoria;

public interface CategoriasRepository extends JpaRepository<Categoria, Integer>{
    
}
