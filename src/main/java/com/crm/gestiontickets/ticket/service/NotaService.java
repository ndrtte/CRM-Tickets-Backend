package com.crm.gestiontickets.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.ticket.entity.HistoricoTicket;
import com.crm.gestiontickets.ticket.entity.Nota;
import com.crm.gestiontickets.ticket.interfaces.INotaService;
import com.crm.gestiontickets.ticket.repository.NotaRepository;

@Service
public class NotaService implements INotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Override
    public void registrarNota(String cuerpoNota, HistoricoTicket historico) {
        Nota nvaNota = new Nota();
        nvaNota.setDescripcion(cuerpoNota);
        nvaNota.setHistoricoTicket(historico);

        notaRepository.save(nvaNota);
    }

    @Override
    public String obtenerNotaHistorico(HistoricoTicket historico){
        Nota nota = notaRepository.findByHistoricoTicket(historico);
        return nota.getDescripcion();
    }

}
