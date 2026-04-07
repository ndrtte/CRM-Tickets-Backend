package com.crm.gestiontickets.reportes.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.reportes.dto.ReporteTicketDTO;
import com.crm.gestiontickets.ticket.repository.TicketRepository;

@Service
public class ReporteTicketService {

    @Autowired
    private TicketRepository ticketRepository;

    private double redondear(Double valor) {
        return valor != null ? Math.round(valor * 10.0) / 10.0 : 0.0;
    }

    public Map<String, Object> resumenGeneral() {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("promedioResolucionHoras", redondear(ticketRepository.promedioResolucionGlobalHoras()));
        res.put("promedioPrimeraRespuestaHoras", redondear(ticketRepository.promedioPrimeraRespuestaHoras()));
        res.put("promedioTiempoAbiertoHoras", redondear(ticketRepository.promedioTiempoAbiertoHoras()));
        return res;
    }

    public List<ReporteTicketDTO> estadisticasPorAgente() {
        return ticketRepository.estadisticasPorAgente();
    }

    public Map<String, Long> conteoPorEstado() {
        Map<String, Long> resultado = new LinkedHashMap<>();
        for (Object[] row : ticketRepository.conteoPorEstado()) {
            resultado.put((String) row[0], ((Number) row[1]).longValue());
        }
        return resultado;
    }

    public Map<String, Long> ticketsPorMes() {
        Map<String, Long> resultado = new LinkedHashMap<>();
        for (Object[] row : ticketRepository.ticketsPorMes()) {
            resultado.put((String) row[0], ((Number) row[1]).longValue());
        }
        return resultado;
    }

}
