/* */
package com.crm.gestiontickets.agente.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.gestiontickets.agente.entity.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
 
  List<Departamento> findByNombreDepartamentoContainingIgnoreCase(String nombre);

    //buscar un departamento:
    @Query("SELECT d FROM Departamento d " +
           "WHERE d.nombreDepartamento LIKE %:valor% " +
           "   OR d.descripcion LIKE %:valor% " +
           "   OR CAST(d.idDepartamento as string) LIKE CONCAT('%', :valor, '%')")
    Page<Departamento> buscarPorCriterio(@Param("valor") String valor, Pageable pageable);

    Page<Departamento> findByActivo(String string, Pageable page);

    List<Departamento> findByNombreDepartamentoContainingIgnoreCaseAndActivo(String nombre, String activo);

}
