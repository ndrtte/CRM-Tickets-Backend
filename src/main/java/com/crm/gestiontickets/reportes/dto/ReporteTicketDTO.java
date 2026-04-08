package com.crm.gestiontickets.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReporteTicketDTO {
    
    private String agenteNombre;

    private Double promedioResolucionHoras;

    private Double promedioPrimeraRespuestaHoras;

    private Double promedioTiempoAbiertoHoras;

    private Long totalTickets;

}
