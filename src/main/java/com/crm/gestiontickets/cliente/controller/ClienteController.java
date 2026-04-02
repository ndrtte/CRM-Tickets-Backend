/* Patrón: estructural: Facade, expone los endpoints REST,
   delega la lógica de negocio al service */
package com.crm.gestiontickets.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.crm.gestiontickets.cliente.entity.Cliente;
import com.crm.gestiontickets.cliente.service.ClienteService;



@CrossOrigin("*")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    
    @GetMapping("/obtener-cliente")
    public Page<ClienteDetalle> obtenerClientes(@RequestParam String valorBusqueda, @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int pageSize) {
        return clienteService.obtenerClientes(valorBusqueda, page, pageSize);
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

    //paginacion
    @GetMapping("/paginado")
     public Page<Cliente> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return clienteService.listarClientes(PageRequest.of(page, size));    
    }

}
