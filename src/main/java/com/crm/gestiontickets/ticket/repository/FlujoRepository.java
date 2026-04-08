package com.crm.gestiontickets.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.Flujo;

@Repository
public interface FlujoRepository extends JpaRepository<Flujo, Integer> {

    // Obtener flujo por categoría
    Flujo findByCategoria(Categoria categoria);

    // Validar si ya existe flujo para una categoría
    boolean existsByCategoria_IdCategoria(Integer idCategoria);

    @Query(value = """
                SELECT COUNT(A.ID_FLUJO)
                FROM TBL_FLUJOS A
                JOIN TBL_CATEGORIAS B ON A.id_categoria = B.id_categoria
                WHERE B.activo = 'S'
            """, nativeQuery = true)
    long countFlujosActivos();
}