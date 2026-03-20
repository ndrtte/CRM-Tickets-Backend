package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.gestiontickets.entity.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
 
  List<Departamento> findByNombreDepartamentoContainingIgnoreCase(String nombre);

    //buscar un departamento:
    @Query("SELECT d FROM Departamento d " +
           "WHERE d.nombreDepartamento LIKE %:valor% " +
           "   OR d.descripcion LIKE %:valor% " +
           "   OR CAST(d.idDepartamento as string) LIKE CONCAT('%', :valor, '%')")
    List<Departamento> buscarPorCriterio(@Param("valor") String valor);

    List<Departamento> findByActivo(String string);

    List<Departamento> findByNombreDepartamentoContainingIgnoreCaseAndActivo(String nombre, String activo);

}
