package com.crm.gestiontickets.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.gestiontickets.dto.EtapaTicket;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.entity.Flujo;
import com.crm.gestiontickets.entity.PasoFlujo;
import com.crm.gestiontickets.repository.FlujoRepository;

@Component
public class PasoFlujoMapper {

    @Autowired
    private FlujoRepository flujoRepository;

    public List<EtapaTicket> mapearEtapas(Categoria categoria, PasoFlujo pasoActual) {
        List<EtapaTicket> listaEtapas = new ArrayList<>();

        if (categoria != null) {
            Flujo flujo = flujoRepository.findByCategoria(categoria);

            if (flujo != null && flujo.getPasos() != null) {

                for (PasoFlujo paso : flujo.getPasos()) {
                    EtapaTicket etapa = new EtapaTicket();
                    etapa.setIdPaso(paso.getIdPasosFlujo());
                    etapa.setDescripcion(paso.getDescripcion());
                    boolean esActual = pasoActual != null && paso.getIdPasosFlujo().equals(pasoActual.getIdPasosFlujo());
                    etapa.setEsActual(esActual);
                    listaEtapas.add(etapa);
                }
            }
        }
        return listaEtapas;
    }
}
