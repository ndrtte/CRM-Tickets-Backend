package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Nota;

public interface NotaRepository extends JpaRepository<Nota, Integer>{
    
}
