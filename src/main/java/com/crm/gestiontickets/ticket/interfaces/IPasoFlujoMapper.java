package com.crm.gestiontickets.ticket.interfaces;

import java.util.List;

import com.crm.gestiontickets.ticket.dto.EtapaTicket;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;

public interface IPasoFlujoMapper {
    
    List<EtapaTicket> mapearEtapas(Categoria categoria, PasoFlujo pasoActual);

}
