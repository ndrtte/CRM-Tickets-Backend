/* Patrón: estructural: Facade, búsqueda de agentes, simplifica la interacción entre controller y 
repositorio, simplifica la obtención y mapeo de datos */
package com.crm.gestiontickets.agente.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.agente.dto.AgenteDepartamento;
import com.crm.gestiontickets.agente.dto.AgenteDetalle;
import com.crm.gestiontickets.agente.entity.Agente;
import com.crm.gestiontickets.agente.entity.Departamento;
import com.crm.gestiontickets.agente.repository.AgenteRepository;
import com.crm.gestiontickets.agente.repository.DepartamentoRepository;
import com.crm.gestiontickets.agente.mapper.AgenteMapper;

@Service
public class AgenteBusquedaService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private AgenteMapper agenteMapper;

    public List<AgenteDepartamento> agentePorDepartamento(Integer idDepartamento) {

        Departamento departamento = departamentoRepository.findById(idDepartamento).get();

        List<Agente> listaAgentes = agenteRepository.findByDepartamentoAndActivo(departamento, "S");

        List<AgenteDepartamento> listaAgenteDetalle = new ArrayList<>();

        for (Agente agente : listaAgentes) {
            AgenteDepartamento agenteDTO = agenteMapper.mapearAgenteDepartamento(agente);
            listaAgenteDetalle.add(agenteDTO);
        }
        return listaAgenteDetalle;
    }
 
    public Page<AgenteDetalle> buscarAgentes(String criterio, int page,int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Agente> agentesPaginados = agenteRepository.buscarPorCriterio(criterio, pageable);
        
        return agentesPaginados.map(agenteMapper::mapearAgenteADetalle);
    }

}
