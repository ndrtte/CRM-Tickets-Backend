package com.crm.gestiontickets.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NuevoCliente {
    private String nombre;

    private String apellido;
    
    private String celular;

    private String correo;

    private String numeroIdentidad;

}
