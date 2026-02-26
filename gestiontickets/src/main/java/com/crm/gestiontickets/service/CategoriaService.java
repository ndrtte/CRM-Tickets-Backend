package com.crm.gestiontickets.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.CategoriaDTO;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> obtenerCategorias() {
        List<CategoriaDTO> listaCategoriasDTO = new ArrayList<>();
        List<Categoria> listaCategorias = categoriaRepository.findAll();

        for (Categoria categoria : listaCategorias) {
            if (categoria.getPadre() == null) {
                listaCategoriasDTO.add(convertirACategoriaDTO(categoria));
            }
        }

        return listaCategoriasDTO;
    }

    private CategoriaDTO convertirACategoriaDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setNombre(categoria.getNombre());

        List<CategoriaDTO> subCategoriasDTO = new ArrayList<>();

        if (categoria.getSubcategorias() != null && !categoria.getSubcategorias().isEmpty()) {
            for (Categoria subcategoria : categoria.getSubcategorias()) {
                subCategoriasDTO.add(convertirACategoriaDTO(subcategoria));
            }
        }
        categoriaDTO.setSubCategorias(subCategoriasDTO);
        return categoriaDTO;
    }

}