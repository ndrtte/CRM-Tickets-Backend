/*Patron:  estructural: Template, genrea los Ids de los tickets*/

package com.crm.gestiontickets.ticket.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.crm.gestiontickets.ticket.interfaces.ISecuencialTicketRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class SecuencialTicketRepository implements ISecuencialTicketRepository {
    
    private final JdbcTemplate jdbcTemplate;

    @Override
    public String generarIdTicket(){
        return jdbcTemplate.queryForObject("EXEC SP_GenerarIdTicket", String.class);
    }

}
