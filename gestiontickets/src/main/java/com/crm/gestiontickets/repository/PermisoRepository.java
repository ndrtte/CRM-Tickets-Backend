package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    
}
