package com.crm.gestiontickets.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoriaDTO {

    private Integer idCategoria;
    private String nombre;

    private List<CategoriaDTO> subCategorias;
}
