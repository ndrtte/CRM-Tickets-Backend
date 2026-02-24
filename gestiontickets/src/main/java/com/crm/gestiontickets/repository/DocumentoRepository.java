package com.crm.gestiontickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {
    
}
