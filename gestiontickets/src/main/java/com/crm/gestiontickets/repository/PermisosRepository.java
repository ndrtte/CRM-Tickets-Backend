package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Permisos;

public interface PermisosRepository extends JpaRepository<Permisos, Integer> {
    
}
