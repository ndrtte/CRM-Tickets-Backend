package com.crm.gestiontickets.service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.CategoriaDetalle;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDetalle> obtenerCategorias() {
        List<CategoriaDetalle> listaCategoriasDTO = new ArrayList<>();
        List<Categoria> listaCategorias = categoriaRepository.findAll();

        for (Categoria categoria : listaCategorias) {
            // Solo categorías raíz
            if (categoria.getPadre() == null) {
                listaCategoriasDTO.add(convertirACategoriaDTO(categoria));
            }
        }

        return listaCategoriasDTO;
    }

    private CategoriaDetalle convertirACategoriaDTO(Categoria categoria) {
        CategoriaDetalle categoriaDTO = new CategoriaDetalle();
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setNombreCategoria(categoria.getNombreCategoria());
        categoriaDTO.setIdCategoriaPadre(categoria.getPadre() != null ? categoria.getPadre().getIdCategoria() : null);
        categoriaDTO.setActivo(categoria.getActivo());

        List<CategoriaDetalle> subCategoriasDTO = new ArrayList<>();
        if (categoria.getSubcategorias() != null) {
            for (Categoria sub : categoria.getSubcategorias()) {
                subCategoriasDTO.add(convertirACategoriaDTO(sub));
            }
        }
        categoriaDTO.setSubCategorias(subCategoriasDTO);

        return categoriaDTO;
    }


    // Crear categoría
    public CategoriaDetalle crearCategoria(CategoriaDetalle dto) {

        if (categoriaRepository.existsByNombreCategoria(dto.getNombreCategoria())) {
            throw new RuntimeException("La categoría ya existe");
        }

        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(dto.getNombreCategoria());
        categoria.setActivo("S"); // activa por defecto
        categoria.setFechaCreacion(LocalDateTime.now());

        // Si tiene categoría padre
        if (dto.getIdCategoriaPadre() != null) {
            Categoria padre = categoriaRepository.findById(dto.getIdCategoriaPadre())
                    .orElseThrow(() -> new RuntimeException("Categoría padre no encontrada"));
            categoria.setPadre(padre);
        }

        categoriaRepository.save(categoria);

        // Devolver DTO
        CategoriaDetalle resultado = new CategoriaDetalle();
        resultado.setIdCategoria(categoria.getIdCategoria());
        resultado.setNombreCategoria(categoria.getNombreCategoria());
        resultado.setIdCategoriaPadre(categoria.getPadre() != null ? categoria.getPadre().getIdCategoria() : null);
        resultado.setActivo(categoria.getActivo());

        return resultado;
    }

    //actualizar categoria
    public CategoriaDetalle actualizarCategoria(Integer idCategoria, CategoriaDetalle dto) {

        Categoria categoria = categoriaRepository
                .findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // validar duplicados
        if (dto.getNombreCategoria() != null &&
                categoriaRepository.existsByNombreCategoriaAndIdCategoriaNot(
                        dto.getNombreCategoria(),
                        idCategoria)) {

            throw new RuntimeException("Otra categoría con ese nombre ya existe");
        }

        // actualizar nombre
        if (dto.getNombreCategoria() != null) {
            categoria.setNombreCategoria(dto.getNombreCategoria());
        }

        // actualizar estado
        if (dto.getActivo() != null) {
            categoria.setActivo(dto.getActivo());
        }

        // actualizar padre
        if (dto.getIdCategoriaPadre() != null) {

            Categoria padre = categoriaRepository
                    .findById(dto.getIdCategoriaPadre())
                    .orElseThrow(() -> new RuntimeException("Categoría padre no encontrada"));

            categoria.setPadre(padre);
        }

        categoria.setFechaActualizacion(LocalDateTime.now());

        categoriaRepository.save(categoria);

        return convertirACategoriaDTO(categoria);
    }

    private CategoriaDetalle convertirACategoriaDTO1(Categoria categoria) {
        CategoriaDetalle dto = new CategoriaDetalle();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setIdCategoriaPadre(categoria.getPadre() != null ? categoria.getPadre().getIdCategoria() : null);
        dto.setActivo(categoria.getActivo());
        return dto;
    }

    // activar o deshabilitar categoria
    public CategoriaDetalle cambiarEstadoCategoria(Integer idCategoria) {   

    Categoria categoria = categoriaRepository.findById(idCategoria)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

    // Cambiar estado
    if ("S".equals(categoria.getActivo())) {
        categoria.setActivo("N");
    } else {
        categoria.setActivo("S");
    }

    categoria.setFechaActualizacion(LocalDateTime.now());

    categoriaRepository.save(categoria);

    return convertirACategoriaDTO(categoria);
}
    
   

}