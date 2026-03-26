package com.crm.gestiontickets.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crm.gestiontickets.shared.dto.Respuesta;
import com.crm.gestiontickets.ticket.dto.TicketAvanzarEtapa;
import com.crm.gestiontickets.ticket.dto.TicketPasoResponse;
import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.repository.PasoFlujoRepository;
import com.crm.gestiontickets.ticket.repository.TicketRepository;
import com.crm.gestiontickets.ticket.service.HistoricoTicketService;
import com.crm.gestiontickets.ticket.service.NotaService;
import com.crm.gestiontickets.ticket.service.TicketFlujoService;

@ExtendWith(MockitoExtension.class)
class TicketFlujoServiceTest {

    @InjectMocks
    private TicketFlujoService ticketFlujoService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PasoFlujoRepository pasoFlujoRepository;

    @Mock
    private HistoricoTicketService historialTicketService;

    @Mock
    private NotaService notaService;

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
