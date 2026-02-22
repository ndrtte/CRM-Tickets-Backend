package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Documentos;

public interface DocumentosRepository extends JpaRepository<Documentos, Integer> {
    
}
