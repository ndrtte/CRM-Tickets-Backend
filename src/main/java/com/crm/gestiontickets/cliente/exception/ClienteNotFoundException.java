/* Patrón: comportamiento: Strategy, manejo de error */

package com.crm.gestiontickets.cliente.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(Long id) {
        super("Cliente no encontrado con id: " + id);
    }
}
