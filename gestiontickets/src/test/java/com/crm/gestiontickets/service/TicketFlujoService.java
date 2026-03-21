package com.crm.gestiontickets.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.crm.gestiontickets.dto.Respuesta;
import com.crm.gestiontickets.dto.ticket.TicketAvanzarEtapa;
import com.crm.gestiontickets.dto.ticket.TicketPasoResponse;
import com.crm.gestiontickets.entity.*;
import com.crm.gestiontickets.repository.*;
import com.crm.gestiontickets.service.ticket.HistorialTicketService;
import com.crm.gestiontickets.service.ticket.NotaService;
import com.crm.gestiontickets.service.ticket.TicketFlujoService;

class TicketFlujoServiceTest {

    @InjectMocks
    private TicketFlujoService ticketFlujoService;

    @Mock
    private TicketRepository ticketRepository;

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
    void testAvanzarEtapa_exitoso() {

        // Arrange
        String idTicket = "TCK-001";

        // Flujo
        Flujo flujo = new Flujo();

        // Paso actual
        PasoFlujo pasoActual = new PasoFlujo();
        pasoActual.setIdPasosFlujo(1);
        pasoActual.setOrden(1);
        pasoActual.setIdFlujo(flujo);

        // Siguiente paso
        PasoFlujo siguientePaso = new PasoFlujo();
        siguientePaso.setIdPasosFlujo(2);
        siguientePaso.setOrden(2);
        siguientePaso.setIdFlujo(flujo);

        // Ticket
        Ticket ticket = new Ticket();
        ticket.setIdTicket(idTicket);
        ticket.setPasoActual(pasoActual);

        TicketAvanzarEtapa dto = new TicketAvanzarEtapa();
        dto.setIdTicket(idTicket);
        dto.setNota("Avanzando etapa");

        // Mocks
        when(ticketRepository.findById(idTicket)).thenReturn(Optional.of(ticket));
        when(pasoFlujoRepository.findByIdFlujoAndOrden(flujo, 2)).thenReturn(siguientePaso);

        // Act
        Respuesta<TicketPasoResponse> respuesta = ticketFlujoService.avanzarEtapa(dto);

        // Assert
        assertTrue(respuesta.isExito());
        assertEquals("Ticket avanzado a la siguiente etapa", respuesta.getMensaje());
        assertNotNull(respuesta.getDatos());
        assertEquals(idTicket, respuesta.getDatos().getIdTicket());
        assertEquals(2, respuesta.getDatos().getIdPaso());

        // Verificaciones
        verify(ticketRepository).save(ticket);
        verify(historialTicketService).registrarHistorico(any(), any(), any(), any(), any());
        verify(notaService).registrarNota(eq("Avanzando etapa"), any());
    }
}