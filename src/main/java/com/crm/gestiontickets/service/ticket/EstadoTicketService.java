package com.crm.gestiontickets.service.ticket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.ticket.EstadoTicketDetalle;
import com.crm.gestiontickets.entity.EstadoTicket;
import com.crm.gestiontickets.repository.EstadoTicketRepository;

@Service
public class EstadoTicketService {

    @Autowired
    private EstadoTicketRepository estadosRepository;
    
    public List<EstadoTicketDetalle> obtenerEstadosTicket(){

        List<EstadoTicket> estados = estadosRepository.findAll();
        List<EstadoTicketDetalle> listaEstadosDTO = new ArrayList<>();

        for (EstadoTicket estadoTicket : estados) {

            EstadoTicketDetalle estadoDTO = new EstadoTicketDetalle(estadoTicket.getIdEstadoTicket(), estadoTicket.getEstadoTicket());
            listaEstadosDTO.add(estadoDTO);
        }

        return listaEstadosDTO;
    }

}
