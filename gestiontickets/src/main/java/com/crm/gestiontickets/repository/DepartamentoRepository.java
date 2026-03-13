package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Departamento;


public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {

    List<Departamento> findByNombreDepartamentoContainingIgnoreCase(String nombre);

}
