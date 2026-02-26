package com.crm.gestiontickets.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class SecuencialTicketRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public String generarIdTicket(){
        return jdbcTemplate.queryForObject("EXEC SP_GenerarIdTicket", String.class);
    }

}
