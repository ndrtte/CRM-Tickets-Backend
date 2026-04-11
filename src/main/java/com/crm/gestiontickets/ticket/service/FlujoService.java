package com.crm.gestiontickets.ticket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.repository.DepartamentoRepository;
import com.crm.gestiontickets.ticket.dto.FlujoDetalle;
import com.crm.gestiontickets.ticket.dto.PasoFlujoDetalle;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.repository.CategoriaRepository;
import com.crm.gestiontickets.ticket.repository.FlujoRepository;

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
    public List<FlujoDetalle> obtenerFlujos() {
        List<Flujo> flujos = flujoRepository.findAll();
        return flujos.stream().map(flujo -> {
            FlujoDetalle dto = new FlujoDetalle();
            dto.setIdFlujo(flujo.getIdFlujo());
            dto.setDescripcion(flujo.getDescripcion());

            Integer idCategoria = flujo.getCategoria() != null ? flujo.getCategoria().getIdCategoria() : null;
            String nombreCategoria = flujo.getCategoria() != null ? flujo.getCategoria().getNombreCategoria() : "Sin categoría";

            dto.setIdCategoria(idCategoria);
            dto.setNombreCategoria(nombreCategoria);
            dto.setActivo(flujo.getActivo());

            List<PasoFlujoDetalle> pasosDTO = flujo.getPasos().stream().map(paso -> {
                PasoFlujoDetalle pasoDTO = new PasoFlujoDetalle();
                pasoDTO.setIdPaso(paso.getIdPasosFlujo());
                pasoDTO.setOrden(paso.getOrden());
                pasoDTO.setDescripcion(paso.getDescripcion());

                Integer idDepartamento = paso.getIdDepartamento() != null ? paso.getIdDepartamento().getIdDepartamento() : null;
                String nombreDepartamento = paso.getIdDepartamento() != null ? paso.getIdDepartamento().getNombreDepartamento() : "Sin departamento";


                pasoDTO.setIdDepartamento(idDepartamento);
                pasoDTO.setNombreDepartamento(nombreDepartamento);
                return pasoDTO;
            }).toList();

            dto.setPasos(pasosDTO);
            return dto;
        }).toList();
    }
    

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
    dto.setActivo(flujo.getActivo());
    dto.setIdFlujo(flujo.getIdFlujo());
    dto.setDescripcion(flujo.getDescripcion());
    dto.setIdCategoria(flujo.getCategoria().getIdCategoria());
    dto.setNombreCategoria(flujo.getCategoria().getNombreCategoria());
    dto.setPasos(flujo.getPasos().stream().map(paso -> {
        PasoFlujoDetalle pasoDTO = new PasoFlujoDetalle();
        pasoDTO.setIdPaso(paso.getIdPasosFlujo());
        pasoDTO.setOrden(paso.getOrden());
        pasoDTO.setDescripcion(paso.getDescripcion());

        Integer idDepartamento = paso.getIdDepartamento() != null ? paso.getIdDepartamento().getIdDepartamento() : null;
        String nombreDepartamento = paso.getIdDepartamento() != null ? paso.getIdDepartamento().getNombreDepartamento() : "Sin departamento";

        pasoDTO.setIdDepartamento(idDepartamento);
        pasoDTO.setNombreDepartamento(nombreDepartamento);
        return pasoDTO;
    }).toList());

    return dto;
}
}