package com.crm.gestiontickets.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.gestiontickets.dto.ClienteDTO;
import com.crm.gestiontickets.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    
    @GetMapping("/obtener-cliente")
    public List<ClienteDTO> obtenerCliente(@RequestParam String valorBusqueda) {
        return clienteService.obtenerCliente(valorBusqueda);
    }

}
