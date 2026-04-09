package com.crm.gestiontickets.ticket.interfaces;

import com.crm.gestiontickets.ticket.entity.HistoricoTicket;

public interface INotaService {
    void registrarNota(String cuerpoNota, HistoricoTicket historico);
    String obtenerNotaHistorico(HistoricoTicket historico);
}
