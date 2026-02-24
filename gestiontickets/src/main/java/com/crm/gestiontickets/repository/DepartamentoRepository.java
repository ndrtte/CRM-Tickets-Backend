package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
    
}
