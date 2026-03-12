package com.crm.gestiontickets.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetalle extends BaseTicketDetalle {
    private Integer idEstado;
    private String estado;
    private LocalDateTime fechaCreacion;
}
