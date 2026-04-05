package com.crm.gestiontickets.flujos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.entity.Departamento;
import com.crm.gestiontickets.agente.repository.DepartamentoRepository;
import com.crm.gestiontickets.flujos.dto.CrearFlujoDTO;
import com.crm.gestiontickets.flujos.entity.Flujo;
import com.crm.gestiontickets.flujos.entity.PasoFlujo;

@Service
public class FlujoService {

    @Autowired
    private FlujoRepository flujoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public void crearFlujo(CrearFlujoDTO dto) {

        // 🔒 Validar nombre (opcional pero recomendado)
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            throw new RuntimeException("El flujo debe tener una descripción");
        }

        Flujo flujo = new Flujo();
        flujo.setDescripcion(dto.getDescripcion());

        // 🔄 Crear pasos
        List<PasoFlujo> pasos = dto.getEtapas().stream().map(etapaDTO -> {

            PasoFlujo paso = new PasoFlujo();

            paso.setDescripcion(etapaDTO.getDescripcion());
            paso.setOrden(etapaDTO.getOrden());

            // 🔗 relación con flujo
            paso.setFlujo(flujo);

            // 🔗 obtener departamento
            Departamento departamento = departamentoRepository
                    .findById(etapaDTO.getIdDepartamento())
                    .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

            paso.setIdDepartamento(departamento);

            return paso;

        }).toList();

        flujo.setPasos(pasos);

        flujoRepository.save(flujo);
    }
}