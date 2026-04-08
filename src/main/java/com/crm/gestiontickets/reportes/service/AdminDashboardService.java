package com.crm.gestiontickets.reportes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.repository.AgenteRepository;
import com.crm.gestiontickets.agente.repository.DepartamentoRepository;
import com.crm.gestiontickets.reportes.dto.AdminDashboardDTO;
import com.crm.gestiontickets.ticket.repository.CategoriaRepository;
import com.crm.gestiontickets.ticket.repository.FlujoRepository;

@Service
public class AdminDashboardService {

    @Autowired
    private FlujoRepository flujoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;
    
    public AdminDashboardDTO obtenerDashboardAdmin() {
        long flujosActivos = flujoRepository.countFlujosActivos();
        long categoriasActivas = categoriaRepository.countByActivo("S");
        long agentesActivos = agenteRepository.countByActivo("S");
        long departamentosActivos = departamentoRepository.countByActivo("S");

        AdminDashboardDTO dashboardDTO = new AdminDashboardDTO(flujosActivos, categoriasActivas, agentesActivos, departamentosActivos);

        return dashboardDTO;
    }

}
