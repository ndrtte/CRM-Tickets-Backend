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

        // Filtro para las categorías que no tienen padre
        for (Categoria categoria : listaCategorias) {
            if (categoria.getPadre() != null) {
                continue;
            }

            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setIdCategoria(categoria.getIdCategoria());
            categoriaDTO.setNombre(categoria.getNombre());

            List<CategoriaDTO> listaSubCategoriaDTO = new ArrayList<>();
            List<Categoria> listaSubcategorias = categoria.getSubcategorias();

            for (Categoria subcategoria : listaSubcategorias) {
                CategoriaDTO subcategoriaDTO = new CategoriaDTO();
                subcategoriaDTO.setIdCategoria(subcategoria.getIdCategoria());
                subcategoriaDTO.setNombre(subcategoria.getNombre());

                listaSubCategoriaDTO.add(subcategoriaDTO);
            }

            categoriaDTO.setSubCategorias(listaSubCategoriaDTO);
            listaCategoriasDTO.add(categoriaDTO);
        }

        return listaCategoriasDTO;
    }

}