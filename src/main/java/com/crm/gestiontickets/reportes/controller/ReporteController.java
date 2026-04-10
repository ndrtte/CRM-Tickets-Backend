package com.crm.gestiontickets.reportes.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.reportes.dto.AdminDashboardDTO;
import com.crm.gestiontickets.reportes.dto.ReporteTicketDTO;
import com.crm.gestiontickets.reportes.service.AdminDashboardService;
import com.crm.gestiontickets.reportes.service.ReporteTicketService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteTicketService reporteService;

    @Autowired
    private AdminDashboardService dashboardService;

    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> resumen() {
        return ResponseEntity.ok(reporteService.resumenGeneral());
    }

    @GetMapping("/por-agente")
    public ResponseEntity<Page<ReporteTicketDTO>> porAgente(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize, 
            @RequestParam(required = false) Integer idDepartamento) {
        return ResponseEntity.ok(reporteService.estadisticasPorAgente(page, pageSize, idDepartamento));
    }

    @GetMapping("/por-estado")
    public ResponseEntity<Map<String, Long>> porEstado(@RequestParam(required=false) Integer idCategoria) {
        return ResponseEntity.ok(reporteService.conteoPorEstado(idCategoria));
    }

    @GetMapping("/por-mes")
    public ResponseEntity<Map<String, Long>> porMes() {
        return ResponseEntity.ok(reporteService.ticketsPorMes());
    }

    @GetMapping("/dashboard-admin")
    public ResponseEntity<AdminDashboardDTO> obtenerDashboardAdmin() {
        return ResponseEntity.ok(dashboardService.obtenerDashboardAdmin());
    }

}
