package com.crm.gestiontickets.service.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.entity.HistoricoTicket;
import com.crm.gestiontickets.entity.Nota;
import com.crm.gestiontickets.entity.Ticket;
import com.crm.gestiontickets.repository.HistoricoTicketRepository;
import com.crm.gestiontickets.repository.NotaRepository;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private HistoricoTicketRepository historicoTicketRepository;

    public void registrarNota(String cuerpoNota, HistoricoTicket historico) {
        Nota nvaNota = new Nota();
        nvaNota.setDescripcion(cuerpoNota);
        nvaNota.setHistoricoTicket(historico);

        notaRepository.save(nvaNota);
    }

    public String obtenerNotaPorHistorico(HistoricoTicket historico) {
        List<Nota> notas = notaRepository.findNotasByHistoricoTicketOrderByIdDesc(historico);
        return notas.isEmpty() ? "" : notas.get(0).getDescripcion();
    }

    public String obtenerNota(Ticket ticket, Integer idPaso) {
        List<HistoricoTicket> historicos = historicoTicketRepository.findHistoricoTicketByTicketYEtapa(ticket.getIdTicket(), idPaso);

        for (HistoricoTicket h : historicos) {
            if (h.getPasoDestino() != null
                    && h.getPasoDestino().getIdPasosFlujo().equals(idPaso)) {

                return obtenerNotaPorHistorico(h);
            }

            if (h.getPasoDestino() == null && h.getPasoOrigen() != null
                    && h.getPasoOrigen().getIdPasosFlujo().equals(idPaso)) {
                return obtenerNotaPorHistorico(h);
            }
        }

        return "";
    }
}
