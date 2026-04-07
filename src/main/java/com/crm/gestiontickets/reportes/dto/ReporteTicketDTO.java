package com.crm.gestiontickets.reportes.dto;

import com.crm.gestiontickets.reportes.interfaces.IReporteTicketDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ReporteTicketDTO implements IReporteTicketDTO{
    
    private String agenteNombre;

    private Double promedioResolucionHoras;

    private Double promedioPrimeraRespuestaHoras;

    private Double promedioTiempoAbiertoHoras;

    private Long totalTickets;

    @Override
    public String getAgenteNombre(){
        return this.agenteNombre;
    }

    @Override
    public Double getPromedioResolucionHoras(){
        return this.promedioResolucionHoras;
    }

    @Override
    public Double getPromedioPrimeraRespuestaHoras(){
        return this.promedioPrimeraRespuestaHoras;
    }

    @Override
    public Double getPromedioTiempoAbiertoHoras(){
        return this.promedioTiempoAbiertoHoras;
    }

    @Override
    public Long getTotalTickets(){
        return this.totalTickets;
    }

}
