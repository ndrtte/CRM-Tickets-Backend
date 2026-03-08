package com.crm.gestiontickets.exception;

public class DepartamentoNotFoundException extends RuntimeException {
    public DepartamentoNotFoundException(Integer idDepartamento) {
        super("Departamento con ID " + idDepartamento + " no encontrado.");
    }
    
}
