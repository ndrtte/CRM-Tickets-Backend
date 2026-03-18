package com.crm.gestiontickets.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crm.gestiontickets.dto.agente.RolDetalle;
import com.crm.gestiontickets.entity.Rol;
import com.crm.gestiontickets.repository.RolRepository;

@ExtendWith(MockitoExtension.class)
class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @Test
    void testObtenerRolesActivos() {
        Rol rol = new Rol();
        rol.setIdRol(1);
        rol.setNombre("ADMIN");
        rol.setDescripcion("Administrador");
        rol.setActivo("S");

        List<Rol> rolesSimulados = new ArrayList<>();
        rolesSimulados.add(rol);

        when(rolRepository.findByActivo("S")).thenReturn(rolesSimulados);

        List<RolDetalle> resultado = rolService.obtenerRolesActivos();

        assertEquals(1, resultado.size());
        assertEquals("ADMIN", resultado.get(0).getNombreRol());
        assertEquals("S", resultado.get(0).getActivo());

        verify(rolRepository).findByActivo("S");
    }
}