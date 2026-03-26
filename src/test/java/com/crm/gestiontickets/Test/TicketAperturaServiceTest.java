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
import com.crm.gestiontickets.ticket.dto.TicketCreacion;
import com.crm.gestiontickets.ticket.dto.TicketPasoResponse;
import com.crm.gestiontickets.ticket.entity.Categoria;
import com.crm.gestiontickets.ticket.entity.EstadoTicket;
import com.crm.gestiontickets.ticket.entity.Flujo;
import com.crm.gestiontickets.ticket.entity.PasoFlujo;
import com.crm.gestiontickets.ticket.entity.Ticket;
import com.crm.gestiontickets.ticket.repository.CategoriaRepository;
import com.crm.gestiontickets.ticket.repository.EstadoTicketRepository;
import com.crm.gestiontickets.ticket.repository.FlujoRepository;
import com.crm.gestiontickets.ticket.repository.PasoFlujoRepository;
import com.crm.gestiontickets.ticket.repository.TicketRepository;
import com.crm.gestiontickets.ticket.service.HistoricoTicketService;
import com.crm.gestiontickets.ticket.service.NotaService;
import com.crm.gestiontickets.ticket.service.TicketAperturaService;

@ExtendWith(MockitoExtension.class)
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
    private HistoricoTicketService historialTicketService;

    @Mock
    private NotaService notaService;

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
