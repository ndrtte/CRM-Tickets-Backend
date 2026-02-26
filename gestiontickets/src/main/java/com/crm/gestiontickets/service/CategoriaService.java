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
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setIdCategoria(categoria.getIdCategoria());
            categoriaDTO.setNombre(categoria.getNombre());

            if(categoria.getPadre() != null){
                Integer idCategoriaPadre = categoria.getPadre().getIdCategoria();
                categoriaDTO.setIdCategoriaPadre(idCategoriaPadre);
            }

            listaCategoriasDTO.add(categoriaDTO);
        }

        return listaCategoriasDTO;
    }

}
