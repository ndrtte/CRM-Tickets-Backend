package com.crm.gestiontickets.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crm.gestiontickets.dto.CategoriaDetalle;
import com.crm.gestiontickets.entity.Categoria;
import com.crm.gestiontickets.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void testCrearCategoria_exitoso() {

        // Arrange
        CategoriaDetalle dto = new CategoriaDetalle();
        dto.setNombreCategoria("Soporte Técnico");

        Categoria categoriaGuardada = new Categoria();
        categoriaGuardada.setIdCategoria(1);
        categoriaGuardada.setNombreCategoria("Soporte Técnico");
        categoriaGuardada.setActivo("S");

        when(categoriaRepository.existsByNombreCategoria("Soporte Técnico"))
                .thenReturn(false);

        when(categoriaRepository.save(any(Categoria.class)))
                .thenReturn(categoriaGuardada);

        // Act
        CategoriaDetalle resultado = categoriaService.crearCategoria(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Soporte Técnico", resultado.getNombreCategoria());
        assertEquals("S", resultado.getActivo());

        verify(categoriaRepository).existsByNombreCategoria("Soporte Técnico");
        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
void testCrearCategoria_duplicada() {

    // Arrange
    CategoriaDetalle dto = new CategoriaDetalle();
    dto.setNombreCategoria("Soporte Técnico");

    when(categoriaRepository.existsByNombreCategoria("Soporte Técnico"))
            .thenReturn(true);

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        categoriaService.crearCategoria(dto);
    });

    assertEquals("La categoría ya existe", exception.getMessage());

    verify(categoriaRepository).existsByNombreCategoria("Soporte Técnico");
    verify(categoriaRepository, never()).save(any());
}
}