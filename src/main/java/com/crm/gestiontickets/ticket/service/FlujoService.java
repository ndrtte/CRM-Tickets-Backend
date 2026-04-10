package com.crm.gestiontickets.ticket.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.ticket.dto.FlujoDetalle;
import com.crm.gestiontickets.ticket.dto.PasoFlujoDetalle;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.repository.CategoriaRepository;
import com.crm.gestiontickets.ticket.repository.FlujoRepository;
import com.crm.gestiontickets.agente.repository.DepartamentoRepository;

import jakarta.transaction.Transactional;

@Service
public class FlujoService {

    @Autowired
    private FlujoRepository flujoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    // CREATE FLUJO CON PASOS
    @Transactional
    public FlujoDetalle crearFlujo(FlujoDetalle dto) {

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Flujo flujo = new Flujo();
        flujo.setDescripcion(dto.getDescripcion());
        flujo.setCategoria(categoria);
        flujo.setFechaCreacion(LocalDateTime.now());

        flujoRepository.save(flujo);

        for (PasoFlujoDetalle pasoDTO : dto.getPasos()) {

            PasoFlujo paso = new PasoFlujo();
            paso.setOrden(pasoDTO.getOrden());
            paso.setDescripcion(pasoDTO.getDescripcion());
            paso.setIdFlujo(flujo);

            paso.setIdDepartamento(
                    departamentoRepository.findById(pasoDTO.getIdDepartamento())
                            .orElseThrow(() -> new RuntimeException("Departamento no encontrado"))
            );

            flujo.getPasos().add(paso);
        }

        flujoRepository.save(flujo);

        return dto;
    }

    // Obtener listado de flujos

    //habilitar o deshabilitar flujo
    @Transactional
public FlujoDetalle cambiarEstadoFlujo(Integer idFlujo) {

    Flujo flujo = flujoRepository.findById(idFlujo)
            .orElseThrow(() -> new RuntimeException("Flujo no encontrado"));

    if ("S".equalsIgnoreCase(flujo.getActivo())) {
        flujo.setActivo("N");
    } else {
        flujo.setActivo("S");
    }

    flujo.setFechaActualizacion(LocalDateTime.now());

    flujoRepository.save(flujo);

    FlujoDetalle dto = new FlujoDetalle();
    dto.setIdFlujo(flujo.getIdFlujo());
    dto.setDescripcion(flujo.getDescripcion());
    dto.setIdCategoria(flujo.getCategoria().getIdCategoria());

    return dto;
}
}