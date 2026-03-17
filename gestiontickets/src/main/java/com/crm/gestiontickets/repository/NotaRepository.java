package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.Nota;

public interface NotaRepository extends JpaRepository<Nota, Integer> {

    List<Nota> findNotasByHistoricoTicketOrderByIdDesc(HistoricoTicket historico);

}
