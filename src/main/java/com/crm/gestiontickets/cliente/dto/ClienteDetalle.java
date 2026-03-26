/* Patrón: creacional: DTO, encapsula los datos para transportarlos entre capas */

package com.crm.gestiontickets.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteDetalle {

    private Long idCliente;
    
    private String nombre;

    private String apellido;
    
    private String celular;

    private String correo;

    private String numeroIdentidad;

}
