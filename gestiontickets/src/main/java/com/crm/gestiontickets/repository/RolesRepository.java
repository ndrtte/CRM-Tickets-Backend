package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Roles;

public interface RolesRepository  extends JpaRepository <Roles, Integer>{
    
}
