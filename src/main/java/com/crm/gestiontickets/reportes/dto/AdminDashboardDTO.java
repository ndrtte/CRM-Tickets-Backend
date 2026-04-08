package com.crm.gestiontickets.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdminDashboardDTO{
    
    private long flujosActivos;
    private long categoriasActivas;
    private long agentesActivos;
    private long departamentosActivos;

}
