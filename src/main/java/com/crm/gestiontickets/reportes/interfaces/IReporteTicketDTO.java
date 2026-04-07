package com.crm.gestiontickets.reportes.interfaces;

public interface IReporteTicketDTO {

    String getAgenteNombre();

    Double getPromedioResolucionHoras();

    Double getPromedioPrimeraRespuestaHoras();

    Double getPromedioTiempoAbiertoHoras();

    Long getTotalTickets();

}
