package com.crm.gestiontickets.ticket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.ticket.dto.CategoriaDetalle;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDetalle> obtenerCategorias() {
        List<CategoriaDetalle> listaCategoriasDTO = new ArrayList<>();
        List<Categoria> listaCategorias = categoriaRepository.findAll();

        for (Categoria categoria : listaCategorias) {
            if (categoria.getPadre() == null) {
                listaCategoriasDTO.add(convertirACategoriaDTO(categoria));
            }
        }

        return listaCategoriasDTO;
    }

    private CategoriaDetalle convertirACategoriaDTO(Categoria categoria) {
        CategoriaDetalle dto = new CategoriaDetalle();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setIdCategoriaPadre(categoria.getPadre() != null ? categoria.getPadre().getIdCategoria() : null);
        dto.setActivo(categoria.getActivo());

        List<CategoriaDetalle> subCategoriasDTO = new ArrayList<>();
        if (categoria.getSubcategorias() != null) {
            for (Categoria sub : categoria.getSubcategorias()) {
                subCategoriasDTO.add(convertirACategoriaDTO(sub));
            }
        }
        dto.setSubCategorias(subCategoriasDTO);

        return dto;
    }

    // Crear categoría
    @Transactional
    public CategoriaDetalle crearCategoria(CategoriaDetalle dto) {
        if (categoriaRepository.existsByNombreCategoria(dto.getNombreCategoria())) {
            throw new RuntimeException("La categoría ya existe");
        }

        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(dto.getNombreCategoria());
        categoria.setActivo("S"); // Activa por defecto
        categoria.setFechaCreacion(LocalDateTime.now());

        // Asignar categoría padre si existe
        if (dto.getIdCategoriaPadre() != null) {
            Categoria padre = categoriaRepository.findById(dto.getIdCategoriaPadre())
                    .orElseThrow(() -> new RuntimeException("Categoría padre no encontrada"));
            categoria.setPadre(padre);
        }

        categoriaRepository.save(categoria);
        return convertirACategoriaDTO(categoria);
    }

    //actualizar categoria
     @Transactional
    public CategoriaDetalle actualizarCategoria(Integer idCategoria, CategoriaDetalle dto) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Validar duplicados
        if (dto.getNombreCategoria() != null &&
            categoriaRepository.existsByNombreCategoriaAndIdCategoriaNot(dto.getNombreCategoria(), idCategoria)) {
            throw new RuntimeException("Otra categoría con ese nombre ya existe");
        }

        // Actualizar nombre
        if (dto.getNombreCategoria() != null) {
            categoria.setNombreCategoria(dto.getNombreCategoria());
        }

        // Actualizar estado si viene en DTO
        if (dto.getActivo() != null) {
            categoria.setActivo(dto.getActivo());
        }

        // Actualizar categoría padre
        if (dto.getIdCategoriaPadre() != null) {
            Categoria padre = categoriaRepository.findById(dto.getIdCategoriaPadre())
                    .orElseThrow(() -> new RuntimeException("Categoría padre no encontrada"));
            categoria.setPadre(padre);
        }

        categoria.setFechaActualizacion(LocalDateTime.now());
        categoriaRepository.save(categoria);

        return convertirACategoriaDTO(categoria);
    }

     @Transactional
    public CategoriaDetalle cambiarEstadoCategoria(Integer idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Cambiar estado
        if ("S".equalsIgnoreCase(categoria.getActivo())) {
            categoria.setActivo("N");
        } else {
            categoria.setActivo("S");
        }

        categoria.setFechaActualizacion(LocalDateTime.now());
        categoriaRepository.save(categoria);

        return convertirACategoriaDTO(categoria);
    
}


}