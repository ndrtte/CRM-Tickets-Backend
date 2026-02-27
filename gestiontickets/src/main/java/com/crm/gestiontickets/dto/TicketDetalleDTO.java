package com.crm.gestiontickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDetalleDTO {
    
    private String idTicket;

    private Long idCliente;

    private Integer idCategoria;

    private Integer idPasoActual;

    private Integer idAgenteAsignado;

    private Character activa;

}
