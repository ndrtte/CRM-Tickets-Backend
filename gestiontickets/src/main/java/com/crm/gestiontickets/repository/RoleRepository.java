package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Rol;

public interface RoleRepository  extends JpaRepository <Rol, Integer>{
    
}
