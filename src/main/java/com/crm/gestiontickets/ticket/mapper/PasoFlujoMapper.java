/*Patron, comportamental: mapper, filtra los ticket de un agente */
package com.crm.gestiontickets.ticket.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.ticket.dto.EtapaTicket;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.repository.FlujoRepository;
import com.crm.gestiontickets.ticket.repository.PasoFlujoRepository;

@Component
public class PasoFlujoMapper {

    @Autowired
    private FlujoRepository flujoRepository;

    @Autowired
    private PasoFlujoRepository pasoFlujoRepository;

    public List<EtapaTicket> mapearEtapas(Categoria categoria, PasoFlujo pasoActual) {
        List<EtapaTicket> listaEtapas = new ArrayList<>();

        PasoFlujo pasoApertura = pasoFlujoRepository.findByDescripcion("APERTURA");
        if (pasoApertura != null) {
            listaEtapas.add(mapearPaso(pasoApertura, pasoActual));
        }

        if (categoria != null) {
            Flujo flujo = flujoRepository.findByCategoria(categoria);

            if (flujo != null && flujo.getPasos() != null) {
                for (PasoFlujo paso : flujo.getPasos()) {
                    listaEtapas.add(mapearPaso(paso, pasoActual));
                }
            }
        }

        return listaEtapas;
    }

    private EtapaTicket mapearPaso(PasoFlujo paso, PasoFlujo pasoActual) {
        EtapaTicket etapa = new EtapaTicket();
        etapa.setIdPaso(paso.getIdPasosFlujo());
        etapa.setDescripcion(paso.getDescripcion());

        boolean esActual = pasoActual != null
                && paso.getIdPasosFlujo().equals(pasoActual.getIdPasosFlujo());

        etapa.setEsActual(esActual);

        return etapa;
    }

}
