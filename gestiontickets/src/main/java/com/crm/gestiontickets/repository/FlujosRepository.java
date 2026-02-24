package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Flujo;

public interface FlujosRepository extends JpaRepository<Flujo, Integer> {
    
}
