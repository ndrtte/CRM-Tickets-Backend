package com.crm.gestiontickets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.Nota;
import com.crm.gestiontickets.repository.NotaRepository;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    public void registrarNota(String cuerpoNota, HistoricoTicket historico) {
        Nota nvaNota = new Nota();
        nvaNota.setDescripcion(cuerpoNota);
        nvaNota.setHistoricoTicket(historico);

        notaRepository.save(nvaNota);
    }

    public String obtenerNotaPorHistorico(HistoricoTicket historico) {
        List<Nota> notas = notaRepository.findNotasByHistoricoTicket(historico);
        return notas.isEmpty() ? "" : notas.get(0).getDescripcion();
    }

}
