package com.crm.gestiontickets.agente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.agente.entity.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    
}
