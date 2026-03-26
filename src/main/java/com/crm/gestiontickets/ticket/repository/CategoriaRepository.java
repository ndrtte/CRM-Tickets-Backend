/*Patron:  Arquitectonico, encapsula CRUD de datos desacopla la logica de negocios de lapersistencia*/
package com.crm.gestiontickets.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.ticket.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
    
    boolean existsByNombreCategoria(String nombreCategoria);

    boolean existsByNombreCategoriaAndIdCategoriaNot(String nombreCategoria, Integer idCategoria);

}
