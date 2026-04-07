package com.crm.gestiontickets.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.gestiontickets.agente.entity.Departamento;
import com.crm.gestiontickets.agente.repository.DepartamentoRepository;
import com.crm.gestiontickets.ticket.dto.CrearFlujoDTO;
import com.crm.gestiontickets.ticket.dto.EtapaDTO;
import com.crm.gestiontickets.ticket.dto.EtapaDetalleDTO;
import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.repository.FlujoRepository;
import com.crm.gestiontickets.ticket.repository.PasoFlujoRepository;

@Service
public class FlujoService {

    @Autowired
    private FlujoRepository flujoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private PasoFlujoRepository pasoFlujoRepository;

   // crear Flujo

    @Transactional
    public void crearFlujo(CrearFlujoDTO dto) {

        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            throw new RuntimeException("El flujo debe tener una descripción");
        }

        if (dto.getEtapas() == null || dto.getEtapas().isEmpty()) {
            throw new RuntimeException("El flujo debe tener al menos una etapa");
        }

        Flujo flujo = new Flujo();
        flujo.setDescripcion(dto.getDescripcion());
        flujo.setEstado(true);

        List<PasoFlujo> pasos = dto.getEtapas().stream().map(etapaDTO -> {

            PasoFlujo paso = new PasoFlujo();

            paso.setDescripcion(etapaDTO.getDescripcion());
            paso.setOrden(etapaDTO.getOrden());
            paso.setEstado(true);
            paso.setFlujo(flujo);

            Departamento departamento = departamentoRepository
                    .findById(etapaDTO.getDepartamento())
                    .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

            paso.setDepartamento(departamento);

            return paso;

        }).toList();

        flujo.setPasos(pasos);

        flujoRepository.save(flujo);
    }


    //desabilitar un flujo

    @Transactional
    public void cambiarEstadoFlujo(Integer idFlujo) {

    Flujo flujo = flujoRepository.findById(idFlujo)
            .orElseThrow(() -> new RuntimeException("Flujo no encontrado"));

    boolean nuevoEstado = !Boolean.TRUE.equals(flujo.getEstado());

    flujo.setEstado(nuevoEstado);

    flujoRepository.save(flujo);
}

 
    // listar las etapas de un flujo

    public List<EtapaDetalleDTO> obtenerEtapasPorFlujo(Integer idFlujo) {

    Flujo flujo = flujoRepository.findById(idFlujo)
            .orElseThrow(() -> new RuntimeException("Flujo no encontrado"));

    return flujo.getPasos().stream()
            .filter(paso -> paso.getEstado() != null && Boolean.TRUE.equals(paso.getEstado())) // opcional: solo activas
            .sorted((a, b) -> ((Integer) a.getOrden()).compareTo((Integer) b.getOrden()))
            .map(paso -> {

                EtapaDetalleDTO dto = new EtapaDetalleDTO();

                dto.setIdPaso(paso.getIdPasosFlujo());
                dto.setDescripcion(paso.getDescripcion());
                dto.setOrden(paso.getOrden());
                dto.setEstado(paso.getEstado());

                dto.setNombreDepartamento(
                        paso.getDepartamento() != null
                                ? ((Departamento) paso.getDepartamento()).getNombre()
                                : null
                );

                return dto;
            })
            .toList();
}

    //habilitar / deshabilitar una etapa
    @Transactional
public void cambiarEstadoPaso(Integer idPaso) {

    PasoFlujo paso = pasoFlujoRepository.findById(idPaso)
            .orElseThrow(() -> new RuntimeException("Paso no encontrado"));

    // Toggle del estado
    boolean nuevoEstado = !Boolean.TRUE.equals(paso.getEstado());

    paso.setEstado(nuevoEstado);

    pasoFlujoRepository.save(paso);
}

// editar una etapa
@Transactional
public void actualizarPaso(Integer idPaso, EtapaDTO dto) {

    PasoFlujo paso = pasoFlujoRepository.findById(idPaso)
            .orElseThrow(() -> new RuntimeException("Etapa no encontrada"));

    paso.setDescripcion(dto.getDescripcion());

    // Opcional: actualizar orden
    if (dto.getOrden() != null) {
        paso.setOrden(dto.getOrden());
    }

    // Si manejas departamento por ID
    if (dto.getDepartamento() != null) {
        Departamento dep = departamentoRepository.findById(dto.getDepartamento())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

        paso.setDepartamento(dep);
    }

    pasoFlujoRepository.save(paso);
}
}