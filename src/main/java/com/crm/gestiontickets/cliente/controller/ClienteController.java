/* Patrón: estructural: Facade, expone los endpoints REST,
   delega la lógica de negocio al service */
package com.crm.gestiontickets.cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.cliente.dto.ClienteDetalle;
import com.crm.gestiontickets.cliente.dto.IdCliente;
import com.crm.gestiontickets.cliente.dto.NuevoCliente;
import com.crm.gestiontickets.cliente.service.ClienteService;



@CrossOrigin("*")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    
    @GetMapping("/obtener-cliente")
    public List<ClienteDetalle> obtenerClientes(@RequestParam String valorBusqueda) {
        return clienteService.obtenerClientes(valorBusqueda);
    }

    @GetMapping("/obtener-cliente-por-id")
    public ClienteDetalle obtenerCliente(@RequestParam Long idCliente) {
        return clienteService.obtenerCliente(idCliente);
    }
    
    @PutMapping("/editar-cliente")
    public IdCliente editarCliente(@RequestBody ClienteDetalle clienteActualizado){
        return clienteService.editarCliente(clienteActualizado);
    }

    @PostMapping("/crear-cliente")
    public IdCliente crearCliente(@RequestBody NuevoCliente nvoClienteDTO) {
        return clienteService.crearCliente(nvoClienteDTO);
    }
    

}
