package com.crm.gestiontickets.ticket.service.factory;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.crm.gestiontickets.ticket.enums.FiltroTicketsAgentesEnum;
import com.crm.gestiontickets.ticket.interfaces.FiltroTicketsAgenteStrategy;
import com.crm.gestiontickets.ticket.service.strategy.FiltroEnProceso;
import com.crm.gestiontickets.ticket.service.strategy.FiltroFinalizados;
import com.crm.gestiontickets.ticket.service.strategy.FiltroTodos;

@Component
public class FiltroFactory{

    private final Map<FiltroTicketsAgentesEnum, FiltroTicketsAgenteStrategy> filtros;

    public FiltroFactory(
            FiltroEnProceso enProceso,
            FiltroFinalizados finalizados,
            FiltroTodos todos) {

        filtros = Map.of(
            FiltroTicketsAgentesEnum.EN_PROCESO, enProceso,
            FiltroTicketsAgentesEnum.FINALIZADOS, finalizados,
            FiltroTicketsAgentesEnum.TODOS, todos
        );
    }

    public FiltroTicketsAgenteStrategy obtenerFiltro(FiltroTicketsAgentesEnum tipo) {
        return filtros.get(tipo);
    }
}
