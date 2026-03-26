package com.crm.gestiontickets.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SolicitudLogin {
    
    private String usuario;

    private String contrasenia;

}
