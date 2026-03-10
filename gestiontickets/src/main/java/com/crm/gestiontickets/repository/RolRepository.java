package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Rol;

public interface RolRepository  extends JpaRepository <Rol, Integer>{
    
}
