package com.crm.gestiontickets.agente.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.agente.entity.Rol;

public interface RolRepository  extends JpaRepository <Rol, Integer>{

    List<Rol> findByActivo(String string);
    
}
