/* Patrón: creacional: DTO, encapsula las respuestas del servise,
   transforma datos y mensajes de forma uniforme */
package com.crm.gestiontickets.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta <R>{
    
    private boolean exito;
    private String mensaje;
    private R datos;



}
