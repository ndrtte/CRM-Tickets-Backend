/*Patron:  Arquitectonico, encapsula CRUD de datos desacopla la logica de negocios de lapersistencia*/

package com.crm.gestiontickets.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.ticket.entity.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {
    
}
