package com.crm.gestiontickets.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.crm.gestiontickets.dto.agente.AgenteDetalle;
import com.crm.gestiontickets.entity.Agente;
import com.crm.gestiontickets.entity.Departamento;
import com.crm.gestiontickets.entity.Rol;
import com.crm.gestiontickets.repository.AgenteRepository;
import com.crm.gestiontickets.repository.DepartamentoRepository;
import com.crm.gestiontickets.repository.RolRepository;

public class AgenteServiceTest {

    @InjectMocks
    private AgenteService agenteService;

    @Mock
    private AgenteRepository agenteRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Mock
    private RolRepository rolRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearAgente_exitoso() {

        // Arrange
        AgenteDetalle dto = new AgenteDetalle();
        dto.setNombre("Carlos");
        dto.setApellido("Ramirez");
        dto.setUsuario("car01");
        dto.setContrasenia("1234");
        dto.setActivo("S");
        dto.setIdDepartamento(1);
        dto.setIdRol(2);

        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(1);

        Rol rol = new Rol();
        rol.setIdRol(2);

        Agente agenteGuardado = new Agente();
        agenteGuardado.setIdAgente(10);
        agenteGuardado.setNombre("Carlos");
        agenteGuardado.setApellido("Ramirez");
        agenteGuardado.setUsuario("car01");
        agenteGuardado.setContrasenia("1234");
        agenteGuardado.setActivo("S");
        agenteGuardado.setDepartamento(departamento);
        agenteGuardado.setRol(rol);

        when(departamentoRepository.findById(1))
                .thenReturn(Optional.of(departamento));

        when(rolRepository.findById(2))
                .thenReturn(Optional.of(rol));

        when(agenteRepository.save(any(Agente.class)))
                .thenReturn(agenteGuardado);

        // Act
        AgenteDetalle resultado = agenteService.crearAgente(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("Ramirez", resultado.getApellido());
        assertEquals("car01", resultado.getUsuario());
        assertEquals("S", resultado.getActivo());
        assertEquals(1, resultado.getIdDepartamento());
        assertEquals(2, resultado.getIdRol());

        verify(departamentoRepository).findById(1);
        verify(rolRepository).findById(2);
        verify(agenteRepository).save(any(Agente.class));
    }
}