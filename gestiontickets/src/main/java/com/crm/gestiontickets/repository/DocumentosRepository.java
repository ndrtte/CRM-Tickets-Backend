package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Documento;

public interface DocumentosRepository extends JpaRepository<Documento, Integer> {
    
}
