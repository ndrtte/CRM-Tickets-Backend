package com.crm.gestiontickets.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crm.gestiontickets.agente.dto.RolDetalle;
import com.crm.gestiontickets.agente.entity.Rol;
import com.crm.gestiontickets.agente.repository.RolRepository;
import com.crm.gestiontickets.agente.service.RolService;

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
