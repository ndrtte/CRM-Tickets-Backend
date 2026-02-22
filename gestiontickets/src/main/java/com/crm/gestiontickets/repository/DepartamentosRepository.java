package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Departamentos;

public interface DepartamentosRepository extends JpaRepository<Departamentos, Integer> {
    
}
