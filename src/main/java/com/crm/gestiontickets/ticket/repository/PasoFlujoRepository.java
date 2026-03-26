package com.crm.gestiontickets.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;


public interface PasoFlujoRepository extends JpaRepository<PasoFlujo, Integer>{
    
    public PasoFlujo findByIdFlujoAndOrden(Flujo idFlujo, Integer orden);

    public List<PasoFlujo> findByIdFlujoOrderByOrdenAsc (Flujo idFlujo);

    public PasoFlujo findFirstByIdFlujoOrderByOrdenAsc(Flujo flujo);

    public PasoFlujo findByDescripcion(String descripcion);

}
