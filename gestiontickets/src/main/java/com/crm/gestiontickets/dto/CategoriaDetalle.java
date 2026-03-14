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
public class CategoriaDetalle {

    private Integer idCategoria;
    private String nombreCategoria;
    private Integer idCategoriaPadre;
    private Integer idFlujo; 
    private String activo;

    private List<CategoriaDetalle> subCategorias; 

}
