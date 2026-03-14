package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.gestiontickets.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

boolean existsByNombreCategoria(String nombreCategoria);

    boolean existsByNombreCategoriaAndIdCategoriaNot(String nombreCategoria, Integer idCategoria);
}
