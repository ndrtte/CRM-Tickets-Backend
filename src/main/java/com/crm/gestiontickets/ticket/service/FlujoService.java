package com.crm.gestiontickets.ticket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.repository.DepartamentoRepository;
import com.crm.gestiontickets.ticket.dto.FlujoDetalle;
import com.crm.gestiontickets.ticket.dto.IdFlujo;
import com.crm.gestiontickets.ticket.dto.PasoFlujoDetalle;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.interfaces.IPasoFlujoService;
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

    @Autowired
    private IPasoFlujoService pasoFlujoService;


    // CREATE FLUJO CON PASOS
    @Transactional
    public FlujoDetalle crearFlujo(FlujoDetalle dto) {

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Flujo flujo = new Flujo();
        flujo.setActivo("S");
        flujo.setDescripcion(dto.getDescripcion());
        flujo.setCategoria(categoria);

        flujoRepository.save(flujo);

        List<PasoFlujo> pasos = new ArrayList<>();

        for (PasoFlujoDetalle pasoDTO : dto.getPasos()) {

            PasoFlujo paso = new PasoFlujo();
            paso.setOrden(pasoDTO.getOrden());
            paso.setDescripcion(pasoDTO.getDescripcion());
            paso.setIdFlujo(flujo);

            paso.setIdDepartamento(
                    departamentoRepository.findById(pasoDTO.getIdDepartamento())
                            .orElseThrow(() -> new RuntimeException("Departamento no encontrado")));

            pasos.add(paso);
        }

        flujo.setPasos(pasos);
        flujoRepository.save(flujo);

        dto.setIdFlujo(flujo.getIdFlujo());
        dto.setActivo(flujo.getActivo());
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setPasos(pasos.stream().map(paso -> {
            PasoFlujoDetalle pasoDTO = new PasoFlujoDetalle();
            pasoDTO.setIdPaso(paso.getIdPasosFlujo());
            pasoDTO.setOrden(paso.getOrden());
            pasoDTO.setDescripcion(paso.getDescripcion());

            Integer idDepartamento = paso.getIdDepartamento() != null ? paso.getIdDepartamento().getIdDepartamento()
                    : null;
            String nombreDepartamento = paso.getIdDepartamento() != null
                    ? paso.getIdDepartamento().getNombreDepartamento()
                    : "Sin departamento";

            pasoDTO.setIdDepartamento(idDepartamento);
            pasoDTO.setNombreDepartamento(nombreDepartamento);
            return pasoDTO;
        }).toList());

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
            String nombreCategoria = flujo.getCategoria() != null ? flujo.getCategoria().getNombreCategoria()
                    : "Sin categoría";

            dto.setIdCategoria(idCategoria);
            dto.setNombreCategoria(nombreCategoria);
            dto.setActivo(flujo.getActivo());

            List<PasoFlujoDetalle> pasosDTO = flujo.getPasos().stream().map(paso -> {
                PasoFlujoDetalle pasoDTO = new PasoFlujoDetalle();
                pasoDTO.setIdPaso(paso.getIdPasosFlujo());
                pasoDTO.setOrden(paso.getOrden());
                pasoDTO.setDescripcion(paso.getDescripcion());

                Integer idDepartamento = paso.getIdDepartamento() != null ? paso.getIdDepartamento().getIdDepartamento()
                        : null;
                String nombreDepartamento = paso.getIdDepartamento() != null
                        ? paso.getIdDepartamento().getNombreDepartamento()
                        : "Sin departamento";

                pasoDTO.setIdDepartamento(idDepartamento);
                pasoDTO.setNombreDepartamento(nombreDepartamento);
                return pasoDTO;
            }).toList();

            dto.setPasos(pasosDTO);
            return dto;
        }).toList();
    }

    @Transactional
    public IdFlujo editarFlujo(Integer idFlujo, FlujoDetalle dto) {

        Flujo flujo = flujoRepository.findById(idFlujo).get();
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria()).get();
        String descripcion = dto.getDescripcion();
        String activo = dto.getActivo();
        List<PasoFlujoDetalle> pasosDTO = dto.getPasos();
        List<PasoFlujo> pasos = pasoFlujoService.editarPasos(pasosDTO);
        
        flujo.setDescripcion(descripcion);
        flujo.setCategoria(categoria);
        flujo.setActivo(activo);
        flujo.setFechaActualizacion(LocalDateTime.now());
        flujo.setPasos(pasos);

        flujoRepository.save(flujo);

        return new IdFlujo(flujo.getIdFlujo());
    }

}