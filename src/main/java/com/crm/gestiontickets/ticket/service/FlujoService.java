package com.crm.gestiontickets.ticket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // Creaar flujo
    @Transactional
    public FlujoDetalle crearFlujo(FlujoDetalle dto) {

    Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    Flujo flujo = new Flujo();
    flujo.setDescripcion(dto.getDescripcion());
    flujo.setCategoria(categoria);
    flujo.setActivo("S");
    flujo.setFechaCreacion(LocalDateTime.now());

    List<PasoFlujo> pasos = new ArrayList<>();

    for (PasoFlujoDetalle pasoDTO : dto.getPasos()) {

        PasoFlujo paso = new PasoFlujo();
        paso.setOrden(pasoDTO.getOrden());
        paso.setDescripcion(pasoDTO.getDescripcion());

        paso.setIdFlujo(flujo); 
        paso.setIdDepartamento(
                departamentoRepository.findById(pasoDTO.getIdDepartamento())
                        .orElseThrow(() -> new RuntimeException("Departamento no encontrado"))
        );

        pasos.add(paso);
    }

    flujo.setPasos(pasos); 

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
            dto.setIdCategoria(flujo.getCategoria().getIdCategoria());
            dto.setNombreCategoria(flujo.getCategoria().getNombreCategoria());
            dto.setActivo(flujo.getActivo());

            List<PasoFlujoDetalle> pasosDTO = flujo.getPasos().stream().map(paso -> {
                PasoFlujoDetalle pasoDTO = new PasoFlujoDetalle();
                pasoDTO.setIdPaso(paso.getIdPasosFlujo());
                pasoDTO.setOrden(paso.getOrden());
                pasoDTO.setDescripcion(paso.getDescripcion());
                pasoDTO.setIdDepartamento(paso.getIdDepartamento().getIdDepartamento());
                pasoDTO.setNombreDepartamento(paso.getIdDepartamento().getNombreCategoria());
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
    dto.setIdFlujo(flujo.getIdFlujo());
    dto.setDescripcion(flujo.getDescripcion());
    dto.setIdCategoria(flujo.getCategoria().getIdCategoria());

    return dto;
}

//obtener flujo por id
public FlujoDetalle obtenerFlujoPorId(Integer idFlujo) {

    Flujo flujo = flujoRepository.findById(idFlujo)
            .orElseThrow(() -> new RuntimeException("Flujo no encontrado"));

    FlujoDetalle dto = new FlujoDetalle();

    dto.setIdFlujo(flujo.getIdFlujo());
    dto.setDescripcion(flujo.getDescripcion());
    dto.setActivo(flujo.getActivo());
    dto.setIdCategoria(flujo.getCategoria().getIdCategoria());
    dto.setNombreCategoria(flujo.getCategoria().getNombreCategoria());

    List<PasoFlujoDetalle> pasosDTO = new ArrayList<>();

    for (PasoFlujo paso : flujo.getPasos()) {

        PasoFlujoDetalle p = new PasoFlujoDetalle();

        p.setOrden(paso.getOrden());
        p.setDescripcion(paso.getDescripcion());
        p.setIdDepartamento(paso.getIdDepartamento().getIdDepartamento());

        pasosDTO.add(p);
    }

    dto.setPasos(pasosDTO);

    return dto;
}

    //Editar un flujo
    @Transactional
public FlujoDetalle actualizarFlujo(Integer idFlujo, FlujoDetalle dto) {

    Flujo flujo = flujoRepository.findById(idFlujo)
            .orElseThrow(() -> new RuntimeException("Flujo no encontrado"));

    flujo.setDescripcion(dto.getDescripcion());
    flujo.setFechaActualizacion(LocalDateTime.now());

    List<PasoFlujo> pasosActualizados = new ArrayList<>();

    for (PasoFlujoDetalle pasoDTO : dto.getPasos()) {

        PasoFlujo paso = new PasoFlujo();

        paso.setOrden(pasoDTO.getOrden());
        paso.setDescripcion(pasoDTO.getDescripcion());

        paso.setIdFlujo(flujo);

        paso.setIdDepartamento(
                departamentoRepository.findById(pasoDTO.getIdDepartamento())
                        .orElseThrow(() -> new RuntimeException("Departamento no encontrado"))
        );

        pasosActualizados.add(paso);
    }

    flujo.getPasos().clear();   
    flujo.getPasos().addAll(pasosActualizados);

    flujoRepository.save(flujo);

    return dto;
}
}