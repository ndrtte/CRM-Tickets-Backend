package com.crm.gestiontickets.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.gestiontickets.dto.PermisoRol;
import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.ResumenAgente;
import com.crm.gestiontickets.dto.SolicitudLogin;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Permiso;
import com.crm.gestiontickets.repository.AgenteRepository;

@Service
public class AuthService {

    @Autowired
    private AgenteRepository agenteRepository;

    public Respuesta<ResumenAgente> inicioSesion(SolicitudLogin credenciales) {
        Agente agente = agenteRepository.findByUsuario(credenciales.getUsuario());

        if (agente == null || !credenciales.getContrasenia().equals(agente.getContrasenia())) {
            return new Respuesta<>(false, "Usuario o contraseña inválidos", null);
        }

        if(agente.getActivo().equals('N')){
            return new Respuesta<>(false, "Usuario desactivado", null);
        }

        List<PermisoRol> listaPermisosRol = new ArrayList<>();

        List<Permiso> listaPermisos = agente.getRol().getPermiso();

        for (Permiso permiso : listaPermisos) {
            PermisoRol permisoRol = new PermisoRol();
            permisoRol.setIdPermiso(permiso.getIdPermiso());
            permisoRol.setCodigo(permiso.getCodigo());
            listaPermisosRol.add(permisoRol);
        }

        String nombre = agente.getNombre() + " " + agente.getApellido();
        ResumenAgente agenteDTO = new ResumenAgente();
        agenteDTO.setIdAgente(agente.getIdAgente());
        agenteDTO.setNombre(nombre);
        agenteDTO.setUsuario(agente.getUsuario());
        agenteDTO.setIdRol(agente.getRol().getIdRol());
        agenteDTO.setRol(agente.getRol().getNombre());
        agenteDTO.setIdDepartamento(agente.getDepartamento().getIdDepartamento());
        agenteDTO.setDepartamento(agente.getDepartamento().getNombreDepartamento());
        agenteDTO.setListaPermisos(listaPermisosRol);

        
        return new Respuesta<>(true, "Inicio de sesión exitoso", agenteDTO);
    }
}
