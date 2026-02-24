package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Permiso;

public interface PermisosRepository extends JpaRepository<Permiso, Integer> {
    
}
