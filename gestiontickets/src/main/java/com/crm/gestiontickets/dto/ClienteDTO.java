package com.crm.gestiontickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class ClienteDTO {

    private Long idCliente;
    
    private String nombre;

    private String apellido;
    
    private String celular;

    private String correo;

    private String numeroIdentidad;

}
