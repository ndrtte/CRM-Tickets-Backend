package com.crm.gestiontickets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.gestiontickets.entity.Agente;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Integer>{
    
    public Agente findByUsuario(String usuario);

    //buscar por nombre, usuario o id_agente
    List<Agente> findByNombreContainingIgnoreCase(String nombre);
    List<Agente> findByUsuarioContainingIgnoreCase(String usuario);
    List<Agente> findByIdAgente(Integer idAgente);

    public List<Agente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrUsuarioContainingIgnoreCase(
            String criterio, String criterio2, String criterio3);

        @Query("SELECT a FROM Agente a " +
           "WHERE a.nombre LIKE %:valor% " +
           "   OR a.apellido LIKE %:valor% " +
           "   OR a.usuario LIKE %:valor%" +
           "   OR CAST(a.idAgente as string) LIKE CONCAT('%', :valor, '%')")
    List<Agente> buscarPorCriterio(@Param("valor") String valor);
}
