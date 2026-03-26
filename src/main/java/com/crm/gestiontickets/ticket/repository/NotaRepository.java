package com.crm.gestiontickets.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.Nota;


public interface NotaRepository extends JpaRepository<Nota, Integer> {

  public Nota findByHistoricoTicket(HistoricoTicket historicoTicket);

}
