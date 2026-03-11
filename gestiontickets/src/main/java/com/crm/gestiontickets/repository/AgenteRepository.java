package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.gestiontickets.entity.Agente;

public interface AgenteRepository extends JpaRepository<Agente, Integer>{
    
    public Agente findByUsuario(String usuario);

    //buscar por nombre, usuario o id_agente
    List<Agente> findByNombreContainingIgnoreCase(String nombre);
    List<Agente> findByUsuarioContainingIgnoreCase(String usuario);
    List<Agente> findByIdAgente(Integer idAgente);

    public List<Agente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrUsuarioContainingIgnoreCase(
            String criterio, String criterio2, String criterio3);
}
