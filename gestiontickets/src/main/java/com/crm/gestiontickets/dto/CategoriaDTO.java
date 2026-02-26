package com.crm.gestiontickets.dto;

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
    private Integer IdCategoriaPadre;
}
