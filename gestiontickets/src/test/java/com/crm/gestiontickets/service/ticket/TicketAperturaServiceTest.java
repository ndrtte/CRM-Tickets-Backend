package com.crm.gestiontickets.service.ticket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.crm.gestiontickets.dto.ticket.TicketCreacion;
import com.crm.gestiontickets.entity.*;
import com.crm.gestiontickets.repository.*;
import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.ticket.TicketPasoResponse;

class TicketAperturaServiceTest {

    @InjectMocks
    private TicketAperturaService ticketAperturaService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private FlujoRepository flujoRepository;

    @Mock
    private PasoFlujoRepository pasoFlujoRepository;

    @Mock
    private EstadoTicketRepository estadoTicketRepository;

    @Mock
    private HistorialTicketService historialTicketService;

    @Mock
    private NotaService notaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearTicket_exitoso() {

        // Arrange
        String idTicket = "TCK-001";

        Ticket ticket = new Ticket();
        ticket.setIdTicket(idTicket);

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);

        Flujo flujo = new Flujo();

        PasoFlujo pasoInicial = new PasoFlujo();
        pasoInicial.setIdPasosFlujo(10);

        EstadoTicket estado = new EstadoTicket();
        estado.setEstadoTicket("En Proceso");

        TicketCreacion dto = new TicketCreacion();
        dto.setIdTicket(idTicket);
        dto.setIdCategoria(1);
        dto.setNota("Nota de prueba");

        // Mocks
        when(ticketRepository.findById(idTicket)).thenReturn(Optional.of(ticket));
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(flujoRepository.findByCategoria(categoria)).thenReturn(flujo);
        when(pasoFlujoRepository.findFirstByIdFlujoOrderByOrdenAsc(flujo)).thenReturn(pasoInicial);
        when(estadoTicketRepository.findByEstadoTicket("En Proceso")).thenReturn(estado);

        // Act
        Respuesta<TicketPasoResponse> respuesta = ticketAperturaService.crearTicket(dto);

        // Assert
        assertTrue(respuesta.isExito());
        assertEquals("Ticket creado correctamente", respuesta.getMensaje());
        assertNotNull(respuesta.getDatos());
        assertEquals(idTicket, respuesta.getDatos().getIdTicket());

        // Verificaciones de interacción
        verify(ticketRepository).save(ticket);
        verify(historialTicketService).registrarHistorico(any(), any(), any(), any(), any());
        verify(notaService).registrarNota(eq("Nota de prueba"), any());
    }
}