package com.crm.gestiontickets.service;

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
            if (categoria.getPadre() == null) {
                listaCategoriasDTO.add(convertirACategoriaDTO(categoria));
            }
        }

        return listaCategoriasDTO;
    }

    private CategoriaDetalle convertirACategoriaDTO(Categoria categoria) {
        CategoriaDetalle categoriaDTO = new CategoriaDetalle();
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setNombre(categoria.getNombre());

        List<CategoriaDetalle> subCategoriasDTO = new ArrayList<>();

        if (categoria.getSubcategorias() != null && !categoria.getSubcategorias().isEmpty()) {
            for (Categoria subcategoria : categoria.getSubcategorias()) {
                subCategoriasDTO.add(convertirACategoriaDTO(subcategoria));
            }
        }
        categoriaDTO.setSubCategorias(subCategoriasDTO);
        return categoriaDTO;
    }

}