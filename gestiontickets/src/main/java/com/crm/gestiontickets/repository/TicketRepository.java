package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Cliente;
import com.crm.gestiontickets.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    public List<Ticket> findByCliente(Cliente cliente);

    @Query("""
        SELECT t 
        FROM Ticket t
        WHERE t.pasoActual.idDepartamento.idDepartamento = :idDepartamento
        """)
    List<Ticket> findTicketsByDepartamento(Integer idDepartamento);

    List<Ticket> findByAgenteAsignado(Agente agente);
}
